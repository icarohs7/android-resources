package base.imageloadingresources.domain.bindingadapters

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import base.imageloadingresources.domain.extensions.load
import coil.api.clear

/**
 * Binding adapter used to load an image
 * into an ImageView on xml
 */
@BindingAdapter("app:url_src")
fun ImageView.loadUrlImage(path: String?) {
    if (path == null) {
        clear()
        return
    }
    load(path)
}