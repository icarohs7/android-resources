package base.dialogresources.presentation.dialogs

import android.content.Context
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.callbacks.onCancel
import com.afollestad.materialdialogs.callbacks.onDismiss
import com.afollestad.materialdialogs.callbacks.onShow
import com.afollestad.materialdialogs.customview.customView
import com.github.icarohs7.unoxcore.extensions.coroutines.cancelCoroutineScope
import com.github.icarohs7.unoxcore.extensions.coroutines.onForeground
import com.nikialeksey.fullscreendialog.DismissOnCloseDialog
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
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
) {
    val lifecycleScope: CoroutineScope = MainScope()
    val binding: T by lazy { createBinding() }
    val dialog: MaterialDialog by lazy { createDialog() }

    private fun createBinding(): T {
        val inflater = context.layoutInflater
        return DataBindingUtil.inflate<T>(inflater, getLayout(), null, false)
                .also { lifecycleScope.launch { onCreateBinding() } }
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

    open fun onShow() {
    }

    open fun onCancel() {
    }

    open fun onDismiss() {
        lifecycleScope.cancelCoroutineScope()
    }

    open fun getMaterialDialog(): MaterialDialog {
        return MaterialDialog(context)
    }

    fun Flow<*>.launchInScope(): Job = launchIn(lifecycleScope)

    @LayoutRes
    abstract fun getLayout(): Int
}