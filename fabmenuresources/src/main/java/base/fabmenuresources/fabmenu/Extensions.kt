package base.fabmenuresources.fabmenu

import android.view.View
import android.view.ViewGroup
import androidx.core.view.plusAssign
import base.corelibrary.presentation.main.BaseMainActivity
import base.fabmenuresources.R
import base.fabmenuresources.databinding.PartialFabSpeeddialBinding
import com.leinardi.android.speeddial.SpeedDialView
import splitties.resources.color
import splitties.systemservices.layoutInflater

/**
 * @receiver Viewgroup the fab will be added on
 * @param contentRoot View that will fade in and out
 *      when the fab is collapsed or expanded, respectively
 */
fun ViewGroup.addSpeedDialFab(contentRoot: View? = null, block: FabBuilder.() -> Unit): SpeedDialView {
    val fabBinding = PartialFabSpeeddialBinding.inflate(context.layoutInflater)
    this += fabBinding.root
    val fab = fabBinding.fab
    fab.setupSpeedDialFab(contentRoot, block)

    return fab
}

/**
 * @receiver SpeedDial to be configured
 * @param contentRoot View that will fade in and out
 *      when the fab is collapsed or expanded, respectively
 */
fun SpeedDialView.setupSpeedDialFab(contentRoot: View?, block: FabBuilder.() -> Unit) {
    mainFabClosedBackgroundColor = color(R.color.colorPrimaryDark)
    mainFabOpenedBackgroundColor = color(R.color.colorPrimaryDark)

    val builder = FabBuilder(this)
    block(builder)

    setOnActionSelectedListener(builder.clickHandler)
    addAllActionItems(builder.menu)

    setOnChangeListener(object : SpeedDialView.OnChangeListener {
        override fun onMainActionSelected(): Boolean {
            return false
        }

        override fun onToggleChanged(isOpen: Boolean) {
            val navHost = contentRoot ?: return
            if (isOpen) fadeContent(navHost) else showContent(navHost)
        }

        private fun showContent(navHost: View): Unit = with(navHost) {
            animate().alpha(1f).duration = 300L
        }

        private fun fadeContent(navHost: View): Unit = with(navHost) {
            animate().alpha(.4f).duration = 300L
        }
    })
}

/**
 * Helper extension used to add a speed dial
 * fab on the main content of an activity
 * inheriting from [BaseMainActivity]
 */
fun BaseMainActivity.addSpeedDialFab(block: FabBuilder.() -> Unit): SpeedDialView = with(binding) {
    val navHost = layoutFragmentContent.findViewById<View>(R.id.nav_host_fragment)
    return layoutFragmentContent.addSpeedDialFab(navHost) {
        onItemClick { menuItem ->
            !onMenuItemSelect(menuItem.id)
        }

        block(this)
    }
}