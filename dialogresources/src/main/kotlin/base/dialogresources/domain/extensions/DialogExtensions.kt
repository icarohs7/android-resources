package base.dialogresources.domain.extensions

import android.app.Dialog
import base.dialogresources.presentation.dialogs.BaseFullscreenMaterialDialog
import base.dialogresources.presentation.dialogs.BaseMaterialDialog
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

suspend inline fun BaseFullscreenMaterialDialog.show(
        block: BaseFullscreenMaterialDialog.() -> Unit
) {
    block()
    show()
}

suspend fun <T> BaseFullscreenMaterialDialog.showWhileRunning(block: suspend () -> T): T {
    return coroutineScope {
        val showJob = launch { show() }
        val result = block()
        showJob.cancel()
        result
    }
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