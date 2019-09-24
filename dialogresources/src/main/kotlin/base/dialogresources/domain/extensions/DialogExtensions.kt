package base.dialogresources.domain.extensions

import android.app.Dialog
import androidx.lifecycle.lifecycleScope
import base.dialogresources.presentation.dialogs.BaseFullscreenMaterialDialog
import base.dialogresources.presentation.dialogs.BaseMaterialDialog
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.coroutines.resume

suspend inline fun BaseFullscreenMaterialDialog.show(
        block: BaseFullscreenMaterialDialog.() -> Unit
) {
    block()
    show()
}

inline fun <D : Dialog, T> D.showWhileRunning(operation: D.() -> T): T {
    return try {
        show()
        operation()
    } finally {
        dismiss()
    }
}

suspend fun <T : BaseMaterialDialog<*>> T.show(block: T.() -> Unit) {
    block()
    show()
}

suspend fun <D : Dialog> D.showAndAwaitDismiss() {
    suspendCancellableCoroutine<Unit> { cont ->
        show()
        setOnDismissListener { cont.resume(Unit) }
        cont.invokeOnCancellation { dismiss() }
    }
}