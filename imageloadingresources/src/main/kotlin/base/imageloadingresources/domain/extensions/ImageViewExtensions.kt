package base.imageloadingresources.domain.extensions

import android.widget.ImageView
import androidx.annotation.DrawableRes
import base.imageloadingresources.R
import coil.api.load
import coil.request.LoadRequestBuilder

/**
 * Load an image at the given url to the
 * receiving ImageView
 * @param rethrowExceptions If false, [onErrorRes] will be
 *          loaded on the ImageView if any error happen,
 *          otherwise any exception will be rethrown
 */
fun ImageView.load(
        url: String,
        @DrawableRes placeholderRes: Int? = R.drawable.img_placeholder_img_loading,
        @DrawableRes onErrorRes: Int? = R.drawable.img_error_img_not_found,
        rethrowExceptions: Boolean = false,
        additionalSetup: LoadRequestBuilder.() -> Unit = { }
) {
    try {
        load(url) {
            placeholderRes?.let(::placeholder)
            onErrorRes?.let(::error)
            crossfade(true)
            additionalSetup()
        }
    } catch (e: Exception) {
        when (rethrowExceptions) {
            true -> throw e
            false -> load(onErrorRes ?: R.drawable.img_error_img_not_found)
        }
    }
}