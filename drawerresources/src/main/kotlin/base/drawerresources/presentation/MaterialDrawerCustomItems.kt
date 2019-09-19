package base.drawerresources.presentation

import android.graphics.Color
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.lifecycleScope
import base.corextresources.R
import base.drawerresources.domain.extensions.id
import base.drawerresources.domain.extensions.updateIntBadgeNoZero
import base.drawerresources.domain.extensions.updateIsEnabled
import co.zsmb.materialdrawerkt.builders.Builder
import co.zsmb.materialdrawerkt.draweritems.badge
import co.zsmb.materialdrawerkt.draweritems.badgeable.PrimaryDrawerItemKt
import co.zsmb.materialdrawerkt.draweritems.badgeable.primaryItem
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
        activity: FragmentActivity,
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
            activity.lifecycleScope.launch(block = onClick)
            true
        }
        extraBuilder()
    }

    badgeTextFlow.onEach { number ->
        drawer()?.apply {
            updateIntBadgeNoZero(R.id.menu_sync, number)
            updateIsEnabled(R.id.menu_sync, number > 0)
        }
    }.launchIn(activity.lifecycleScope)

    return item
}