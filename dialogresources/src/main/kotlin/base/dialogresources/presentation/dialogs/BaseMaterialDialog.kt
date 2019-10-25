package base.dialogresources.presentation.dialogs

import android.content.Context
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.lifecycle.lifecycleScope
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.callbacks.onCancel
import com.afollestad.materialdialogs.callbacks.onDismiss
import com.afollestad.materialdialogs.callbacks.onShow
import com.afollestad.materialdialogs.customview.customView
import com.github.icarohs7.unoxcore.extensions.coroutines.onForeground
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import splitties.systemservices.layoutInflater
import kotlin.coroutines.resume

/**
 * Base class used to hold and handle
 * bottom sheets
 */
abstract class BaseMaterialDialog<T : ViewDataBinding>(
    protected val context: Context
) : LifecycleOwner {
    private val lifecycleRegistry by lazy { LifecycleRegistry(this) }
    val binding: T by lazy { createBinding() }
    protected val dialog: MaterialDialog by lazy { createDialog() }

    private fun createBinding(): T {
        val inflater = context.layoutInflater
        return DataBindingUtil.inflate<T>(inflater, getLayout(), null, false)
            .apply { lifecycleOwner = this@BaseMaterialDialog }
            .also { lifecycleScope.launchWhenCreated { onCreateBinding() } }
    }

    private fun createDialog(): MaterialDialog {
        return getMaterialDialog()
            .customView(view = binding.root, noVerticalPadding = true)
            .onCancel { onCancel() }
            .onDismiss { onDismiss() }
            .onShow { onShow() }
    }

    /**
     * Called after the binding is created,
     * either by a need of the dialog or for
     * another reason
     */
    abstract suspend fun onCreateBinding()

    fun show(scope: CoroutineScope): Job {
        return scope.launch { show() }
    }

    suspend fun show() {
        onForeground {
            suspendCancellableCoroutine<Unit> { cont ->
                dialog.show()
                dialog.onDismiss { cont.resume(Unit) }
                cont.invokeOnCancellation { dialog.dismiss() }
            }
        }
    }

    fun dismiss() {
        dialog.dismiss()
    }

    open fun onShow() {
        lifecycleRegistry.currentState = Lifecycle.State.RESUMED
    }

    open fun onCancel() {
        lifecycleRegistry.currentState = Lifecycle.State.DESTROYED
    }

    open fun onDismiss() {
        lifecycleRegistry.currentState = Lifecycle.State.DESTROYED
    }

    open fun getMaterialDialog(): MaterialDialog = MaterialDialog(context)
    @LayoutRes abstract fun getLayout(): Int
    override fun getLifecycle(): Lifecycle = lifecycleRegistry
}