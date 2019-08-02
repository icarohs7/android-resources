package base.drawableresources.domain.extensions

import android.graphics.Color
import android.view.View
import androidx.annotation.ColorInt
import base.drawableresources.domain.toplevel.rippleBackgroundDrawable

/**
 * Add a ripple background to the view and define
 * its click listener
 */
fun View.rippleOnClick(@ColorInt rippleColor: Int = Color.parseColor("#888888"), listener: (View) -> Unit) {
    background = rippleBackgroundDrawable(rippleColor).build()
    setOnClickListener(listener)
}