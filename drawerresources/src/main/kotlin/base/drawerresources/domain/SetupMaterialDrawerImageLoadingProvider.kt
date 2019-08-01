package base.drawerresources.domain

/*
Adding support to image loading using url to account header:

1st.
Add the dependency to support InitProviders
implementation(AndroidDeps.splittiesInitprovider)

2nd.
- Create the following provider that will setup the loading

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

3rd.
- Add the provider to the manifest:
<application>
    <provider
        android:name=".domain.SetupMaterialDrawerImageLoadingProvider"
        android:authorities="${applicationId}.setupmaterialdrawerimageloadingprovider"
        android:directBootAware="true"
        android:exported="false"
        android:initOrder="900"
        tools:ignore="UnusedAttribute" />
</application>
*/