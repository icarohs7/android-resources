package base.drawerresources.domain.extensions

import arrow.core.Try
import base.corextresources.R
import base.corextresources.databinding.NavHeaderBinding
import co.zsmb.materialdrawerkt.builders.DrawerBuilderKt
import co.zsmb.materialdrawerkt.draweritems.badge
import co.zsmb.materialdrawerkt.draweritems.badgeable.BadgeableKt
import co.zsmb.materialdrawerkt.draweritems.base.AbstractDrawerItemKt
import com.mikepenz.materialdrawer.Drawer
import com.mikepenz.materialdrawer.holder.StringHolder
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem

var AbstractDrawerItemKt<*>.id: Int
    get() = this.identifier.toInt()
    set(value) {
        identifier = value.toLong()
    }

operator fun Drawer.get(itemId: Int): IDrawerItem<*>? {
    return getDrawerItem(itemId.toLong())
}

fun Drawer.updateBadge(itemId: Int, badgeText: String?) {
    val strHolder = StringHolder(badgeText)
    updateBadge(itemId.toLong(), strHolder)
}

fun Drawer.updateIntBadgeNoZero(itemId: Int, number: Int) {
    if (number > 0) updateBadge(itemId, "$number")
    else updateBadge(itemId, null)
}

fun Drawer.updateIsEnabled(itemId: Int, isEnabled: Boolean): Try<Unit> {
    return updateItem(itemId) { apply { this.isEnabled = isEnabled } }
}

fun Drawer.updateItem(itemId: Int, block: IDrawerItem<*>.() -> Any): Try<Unit> {
    return Try {
        updateItem(this[itemId]!!.block() as IDrawerItem<*>)
    }
}

fun DrawerBuilderKt.defaultHeader() {
    headerView = NavHeaderBinding.inflate(activity.layoutInflater).root
}

fun BadgeableKt.defaultNumberBadge(initialValue: Number) {
    badge("$initialValue") {
        colorRes = R.color.colorError
        textColorRes = R.color.white
    }
}