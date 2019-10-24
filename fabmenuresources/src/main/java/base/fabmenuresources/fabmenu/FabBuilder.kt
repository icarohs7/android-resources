package base.fabmenuresources.fabmenu

import androidx.annotation.IdRes
import com.leinardi.android.speeddial.SpeedDialActionItem
import com.leinardi.android.speeddial.SpeedDialView
import java.util.ArrayDeque

class FabBuilder internal constructor(private val fab: SpeedDialView) {
    internal val menu: ArrayDeque<SpeedDialActionItem> = ArrayDeque()
    internal var clickHandler: (SpeedDialActionItem) -> Boolean = { false }

    fun onItemClick(clickHandler: (SpeedDialActionItem) -> Boolean) {
        this.clickHandler = clickHandler
    }

    fun withFab(block: SpeedDialView.() -> Unit) {
        block(fab)
    }

    fun menuItem(
        @IdRes id: Int,
        block: FabItemBuilder.() -> Unit
    ) {
        val builder = FabItemBuilder(fab.context, id)
        block(builder)
        menu.push(builder.build())
    }

    fun build(): List<SpeedDialActionItem> = menu.toList()
}