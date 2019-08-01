package base.drawerresources.presentation

import android.graphics.Color
import base.corelibrary.R
import base.drawerresources.domain.extensions.id
import base.drawerresources.domain.extensions.updateIntBadgeNoZero
import co.zsmb.materialdrawerkt.builders.Builder
import co.zsmb.materialdrawerkt.draweritems.badge
import co.zsmb.materialdrawerkt.draweritems.badgeable.PrimaryDrawerItemKt
import co.zsmb.materialdrawerkt.draweritems.badgeable.primaryItem
import com.github.icarohs7.unoxandroidarch.presentation.activities.BaseScopedActivity
import com.mikepenz.google_material_typeface_library.GoogleMaterial
import com.mikepenz.materialdrawer.Drawer
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
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
        badgeTextFlow: Flow<Int>,
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

    badgeTextFlow.onEach { number ->
        drawer()?.updateIntBadgeNoZero(R.id.menu_sync, number)
    }.launchIn(activity)

    return item
}