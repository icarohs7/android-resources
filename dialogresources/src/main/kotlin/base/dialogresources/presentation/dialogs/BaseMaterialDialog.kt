package base.dialogresources.presentation.dialogs

import android.content.Context
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.callbacks.onCancel
import com.afollestad.materialdialogs.callbacks.onDismiss
import com.afollestad.materialdialogs.customview.customView
import com.github.icarohs7.unoxcore.extensions.addTo
import com.github.icarohs7.unoxcore.extensions.coroutines.cancelCoroutineScope
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
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

    val binding: T by lazy {
        DataBindingUtil.inflate<T>(context.layoutInflater, getLayout(), null, false)
                .also { launch { onCreateBinding() } }
    }
    val dialog: MaterialDialog by lazy {
        getMaterialDialog()
                .customView(view = binding.root, noVerticalPadding = true)
                .onCancel { onCancel() }
                .onDismiss { onDismiss() }
    }

    fun show() {
        dialog.show()
    }

    /**
     * Called after the binding is created,
     * either by a need of the dialog or for
     * another reason
     */
    abstract suspend fun onCreateBinding()

    open fun onCancel() {
    }

    open fun onDismiss() {
        cancelCoroutineScope()
        compositeDisposable.clear()
    }

    open fun getMaterialDialog(): MaterialDialog {
        return MaterialDialog(context)
    }

    fun Disposable.disposeOnDismiss() {
        this.addTo(compositeDisposable)
    }

    @LayoutRes
    abstract fun getLayout(): Int
}