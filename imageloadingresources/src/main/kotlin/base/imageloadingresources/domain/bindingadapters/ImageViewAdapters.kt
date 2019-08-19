package base.imageloadingresources.domain.bindingadapters

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import base.imageloadingresources.domain.extensions.load

/**
 * Binding adapter used to load an image
 * into an ImageView on xml
 */
@BindingAdapter("app:url_src")
fun ImageView.loadUrlImage(path: String) {
    load(path)
}