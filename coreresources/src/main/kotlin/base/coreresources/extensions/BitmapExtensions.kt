package base.coreresources.extensions

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.core.content.FileProvider
import arrow.core.Try
import com.github.icarohs7.unoxcore.extensions.orThrow
import com.github.icarohs7.unoxcore.toplevel.tryBg
import java.io.File
import java.io.FileOutputStream
import kotlin.math.round

/**
 * Split the given bitmap in parts, each not
 * exceeding the [partMaxHeight] height
 */
fun Bitmap.splitIntoSequence(partMaxHeight: Int): Sequence<Bitmap> {
    return sequence {
        var heightLeft = height
        while (heightLeft > partMaxHeight) {
            yield(cutHeight(height - heightLeft, partMaxHeight))
            heightLeft -= partMaxHeight
        }
        yield(cutHeight(height - heightLeft, heightLeft))
    }
}

/**
 * Cut horizontally the given bitmap, returning
 * the new bitmap created
 * @param offset After how many pixels after the start of the
 *              cut will start
 * @param partHeight Height of the cut
 */
fun Bitmap.cutHeight(offset: Int, partHeight: Int): Bitmap {
    return Bitmap.createBitmap(this, 0, offset, width, partHeight)
}

/**
 * Scale the given bitmap to a new size, keeping it's
 * aspect ratio
 * @param newWidth Width to be scaled to
 */
fun Bitmap.resizeKeepingAspectRatio(newWidth: Int): Bitmap {
    val aspectRatio = width.toDouble() / height
    val newHeight = round(newWidth / aspectRatio).toInt()
    return Bitmap.createScaledBitmap(this, newWidth, newHeight, false)
}

/**
 * Open a share dialog to share the given image
 * ### Need to add content provider to manifest to work:
 * ```
 * <provider
 *     android:name="androidx.core.content.FileProvider"
 *     android:authorities="com.mydomain.fileprovider"
 *     android:exported="false"
 *     android:grantUriPermissions="true">
 *     <meta-data
 *         android:name="android.support.FILE_PROVIDER_PATHS"
 *         android:resource="@xml/file_paths" />
 * </provider>
 * ```
 * ### Also, need to create the file file_paths.xml under the xml folder on res with the given content:
 * ```
 * <?xml version="1.0" encoding="utf-8"?>
 * <resources>
 *     <paths>
 *         <cache-path
 *             name="shared_images"
 *             path="images/" />
 *     </paths>
 * </resources>
 * ```
 * @param providerUri Uri used for the content provider, e.g com.mydomain.fileprovider
 */
suspend fun Bitmap.share(context: Context, providerUri: String, shareSheetTitle: String): Try<Unit> {
    return tryBg {
        val uri = saveToContentProvider(context, providerUri, "shared_image.png").orThrow()
        val intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_STREAM, uri)
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            type = "image/*"
        }
        context.startActivity(Intent.createChooser(intent, shareSheetTitle))
    }
}

/**
 * Save the given Bitmap to a content provider using the
 * given name for the file
 * ### Need to add content provider to manifest to work:
 * ```
 * <provider
 *     android:name="androidx.core.content.FileProvider"
 *     android:authorities="com.mydomain.fileprovider"
 *     android:exported="false"
 *     android:grantUriPermissions="true">
 *     <meta-data
 *         android:name="android.support.FILE_PROVIDER_PATHS"
 *         android:resource="@xml/file_paths" />
 * </provider>
 * ```
 * ### Also, need to create the file file_paths.xml under the xml folder on res with the given content:
 * ```
 * <?xml version="1.0" encoding="utf-8"?>
 * <resources>
 *     <paths>
 *         <cache-path
 *             name="shared_images"
 *             path="images/" />
 *     </paths>
 * </resources>
 * ```
 * @param providerUri Uri used for the content provider, e.g com.mydomain.fileprovider
 * @param fileName Name used by the saved file
 */
suspend fun Bitmap.saveToContentProvider(context: Context, providerUri: String, fileName: String): Try<Uri> {
    return tryBg {
        val imagesFolder = File(context.cacheDir, "images")
        imagesFolder.mkdirs()
        val file = File(imagesFolder, fileName)
        FileOutputStream(file).apply {
            compress(Bitmap.CompressFormat.PNG, 90, this)
            flush()
            close()
        }
        FileProvider.getUriForFile(context, providerUri, file)
    }
}