package base.coreresources.presentation.activities

import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.widget.Toolbar
import base.coreresources.AppEventBus
import base.coreresources.toplevel.onActivity
import com.airbnb.mvrx.BaseMvRxActivity

/**
 * Activity containing a coroutine scope,
 * cancelling it and all children coroutines
 * when destroyed
 */
abstract class BaseArchActivity : BaseMvRxActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setSoftInputMode(onSetSoftInputMode())
        AppEventBus.Out.subscribeActivity(this)
    }

    open fun setupToolbar(toolbar: Toolbar) {
        setSupportActionBar(toolbar)
    }

    /**
     * Define how the window will behave when the soft
     * keyboard is open, defaulting to
     * [WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN]
     */
    open fun onSetSoftInputMode(): Int {
        return WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN
    }

    companion object {
        operator fun invoke(block: BaseArchActivity.() -> Unit): Unit = onActivity(block)
        fun setupToolbar(toolbar: Toolbar): Unit = invoke { this.setupToolbar(toolbar) }
    }
}