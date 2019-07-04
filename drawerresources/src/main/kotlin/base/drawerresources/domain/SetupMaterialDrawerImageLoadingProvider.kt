package base.drawerresources.domain

import android.content.Context
import android.graphics.drawable.Drawable
import android.net.Uri
import android.util.Log
import android.widget.ImageView
import com.mikepenz.materialdrawer.util.AbstractDrawerImageLoader
import com.mikepenz.materialdrawer.util.DrawerImageLoader
import com.mikepenz.materialdrawer.util.DrawerUIUtils
import com.squareup.picasso.Picasso
import splitties.initprovider.InitProvider

class SetupMaterialDrawerImageLoadingProvider : InitProvider() {
    override fun onCreate(): Boolean {
        DrawerImageLoader.init(object : AbstractDrawerImageLoader() {
            private val picasso by lazy { Picasso.get() }

            override fun placeholder(ctx: Context, tag: String?): Drawable {
                return DrawerUIUtils.getPlaceHolder(ctx)
            }

            override fun set(imageView: ImageView, uri: Uri, placeholder: Drawable?, tag: String?) {
                picasso.load(uri)
                        .also { placeholder?.let(it::placeholder) }
                        .into(imageView)
            }

            override fun cancel(imageView: ImageView) {
                picasso.cancelRequest(imageView)
            }
        })
        return true
    }
}