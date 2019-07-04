package base.fabmenuresources.fabmenu

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.IdRes
import androidx.annotation.StringRes
import com.leinardi.android.speeddial.SpeedDialActionItem
import splitties.resources.color
import splitties.resources.drawable
import splitties.resources.str

class FabItemBuilder internal constructor(private val context: Context, @IdRes private val id: Int) {
    private var innerBuilderSetup: SpeedDialActionItem.Builder.() -> Unit = {}

    var title: String = ""
    @StringRes var titleRes: Int = 0
        @Deprecated("Getter shouldn't be used") get
        set(value) {
            if (value > 0)
                title = context.str(value)
        }

    @ColorInt var titleColor: Int = Int.MIN_VALUE
    @ColorRes var titleColorRes: Int = 0
        @Deprecated("Getter shouldn't be used") get
        set(value) {
            if (value > 0)
                titleColor = context.color(value)
        }

    @ColorInt var backgroundColor: Int = Int.MIN_VALUE
    @ColorRes var backgroundColorRes: Int = 0
        @Deprecated("Getter shouldn't be used") get
        set(value) {
            if (value > 0)
                backgroundColor = context.color(value)
        }

    var icon: Drawable? = null
    @DrawableRes var iconRes: Int = 0
        @Deprecated("Getter shouldn't be used") get
        set(value) {
            if (value > 0)
                icon = context.drawable(value)
        }

    @ColorInt var iconTint: Int = Int.MIN_VALUE
    @ColorRes var iconTintRes: Int = 0
        @Deprecated("Getter shouldn't be used") get
        set(value) {
            if (value > 0)
                iconTint = context.color(value)
        }

    fun extraSetup(block: SpeedDialActionItem.Builder.() -> Unit) {
        this.innerBuilderSetup = block
    }

    fun build(): SpeedDialActionItem {
        return SpeedDialActionItem.Builder(id, icon).apply {
            setLabel(title)
            using(titleColor) { setLabelColor(it) }
            using(backgroundColor) { setFabBackgroundColor(it) }
            using(iconTint) { setFabImageTintColor(it) }
            innerBuilderSetup(this)
        }.create()
    }

    private fun using(value: Int, block: (Int) -> Unit) {
        if (value != Int.MIN_VALUE)
            block(value)
    }
}