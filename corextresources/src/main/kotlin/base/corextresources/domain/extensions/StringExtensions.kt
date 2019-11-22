package base.corextresources.domain.extensions

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import com.github.icarohs7.unoxcore.extensions.coroutines.onBackground
import kotlinx.serialization.ImplicitReflectionSerializer
import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.parse
import java.text.Normalizer

fun <T : Any> String.deserialize(deserializer: KSerializer<T>): T {
    return Json.parse(deserializer, this)
}

@ImplicitReflectionSerializer
inline fun <reified T : Any> String.deserialize(): T {
    return Json.parse(this)
}

/**
 * Replace all accented characters on the given
 * string by their unaccented counterparts
 */
fun CharSequence.unaccented(): String {
    val regexUnaccent = "\\p{InCombiningDiacriticalMarks}+".toRegex()
    val temp = Normalizer.normalize(this, Normalizer.Form.NFD)
    return regexUnaccent.replace(temp, "")
}


/**
 * Decode the given string from base64 and convert
 * the image into a Bitmap
 */
suspend fun String.base64ToBitmap(): Bitmap {
    return onBackground {
        val bytes = Base64.decode(substringAfter(','), Base64.DEFAULT)
        BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
    }
}