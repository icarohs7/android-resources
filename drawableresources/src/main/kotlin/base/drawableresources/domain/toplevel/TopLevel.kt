package base.drawableresources.domain.toplevel

import androidx.annotation.ColorInt
import top.defaults.drawabletoolbox.DrawableBuilder

/**
 * Create a [DrawableBuilder] with a preset ripple
 * effect of the given color
 */
fun rippleBackgroundDrawable(@ColorInt color: Int): DrawableBuilder {
    return DrawableBuilder()
            .ripple()
            .rippleColor(color)
}