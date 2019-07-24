package base.animationresources.domain.extensions

import android.view.View
import com.github.florent37.viewanimator.AnimationBuilder
import com.github.florent37.viewanimator.ViewAnimator

fun View.viewAnimate(): AnimationBuilder {
    return ViewAnimator.animate(this)
}