package base.dialogresources.presentation.dialogs

import android.content.Context
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.callbacks.onCancel
import com.afollestad.materialdialogs.callbacks.onDismiss
import com.afollestad.materialdialogs.customview.customView
import com.github.icarohs7.unoxcore.extensions.coroutines.cancelCoroutineScope
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.launch
import splitties.systemservices.layoutInflater

/**
 * Base class used to hold and handle
 * bottom sheets
 */
abstract class BaseMaterialDialog<T : ViewDataBinding>(
        protected val context: Context
) : CoroutineScope by MainScope() {
    private val compositeDisposable = CompositeDisposable()
    val binding: T by lazy { createBinding() }
    val dialog: MaterialDialog by lazy { createDialog() }

    private fun createBinding(): T {
        val inflater = context.layoutInflater
        return DataBindingUtil.inflate<T>(inflater, getLayout(), null, false)
                .also { launch { onCreateBinding() } }
    }

    private fun createDialog(): MaterialDialog {
        return getMaterialDialog()
                .customView(view = binding.root, noVerticalPadding = true)
                .onCancel { onCancel() }
                .onDismiss { onDismiss() }
    }

    /**
     * Called after the binding is created,
     * either by a need of the dialog or for
     * another reason
     */
    abstract suspend fun onCreateBinding()

    fun show() {
        dialog.show()
    }

    open fun onCancel() {
    }

    open fun onDismiss() {
        cancelCoroutineScope()
        compositeDisposable.clear()
    }

    open fun getMaterialDialog(): MaterialDialog {
        return MaterialDialog(context)
    }

    fun Flow<*>.launchInScope(): Job = launchIn(this@BaseMaterialDialog)

    @LayoutRes
    abstract fun getLayout(): Int
}