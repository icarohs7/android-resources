package base.drawerresources.presentation

import android.graphics.Color
import base.corelibrary.R
import base.drawerresources.domain.id
import base.drawerresources.domain.updateIntBadgeNoZero
import co.zsmb.materialdrawerkt.builders.Builder
import co.zsmb.materialdrawerkt.draweritems.badge
import co.zsmb.materialdrawerkt.draweritems.badgeable.PrimaryDrawerItemKt
import co.zsmb.materialdrawerkt.draweritems.badgeable.primaryItem
import com.github.icarohs7.unoxandroidarch.presentation.activities.BaseScopedActivity
import com.github.icarohs7.unoxcore.extensions.setupAndroidSchedulers
import com.mikepenz.google_material_typeface_library.GoogleMaterial
import com.mikepenz.materialdrawer.Drawer
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem
import io.reactivex.Flowable
import io.sellmair.disposer.disposeBy
import io.sellmair.disposer.onDestroy
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

fun Builder.disconnectButton(extraBuilder: PrimaryDrawerItemKt.() -> Unit = {}): PrimaryDrawerItem {
    return primaryItem(R.string.desconectar) {
        id = R.id.menu_logout
        iicon = GoogleMaterial.Icon.gmd_exit_to_app
        selectable = false
        extraBuilder()
    }
}

fun Builder.synchronizeButton(
        activity: BaseScopedActivity,
        drawer: () -> Drawer?,
        badgeTextFlowable: Flowable<Int>,
        onClick: suspend CoroutineScope.() -> Unit,
        extraBuilder: PrimaryDrawerItemKt.() -> Unit = {}
): PrimaryDrawerItem {
    val item = primaryItem(R.string.sincronizar) {
        id = R.id.menu_sync
        iicon = GoogleMaterial.Icon.gmd_sync
        selectable = false
        badge("0") {
            colorRes = R.color.colorError
            textColor = Color.WHITE.toLong()
        }
        onClick { _ ->
            activity.launch(block = onClick)
            true
        }
        extraBuilder()
    }

    badgeTextFlowable.setupAndroidSchedulers().subscribe { number ->
        drawer()?.updateIntBadgeNoZero(R.id.menu_sync, number)
    }.disposeBy(activity.onDestroy)

    return item
}