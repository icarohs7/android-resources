package base.dialogresources.domain.extensions

import android.app.Dialog
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

inline fun <T> BaseFullscreenMaterialDialog.showWhileRunning(operation: BaseFullscreenMaterialDialog.() -> T): T {
    return try {
        dialog.show()
        operation()
    } finally {
        dialog.dismiss()
    }
}

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

/**
 * Launch a coroutine tied to the lifecycle
 * of the given dialog
 */
fun BaseMaterialDialog<*>.launch(
        context: CoroutineContext = EmptyCoroutineContext,
        start: CoroutineStart = CoroutineStart.DEFAULT,
        block: suspend CoroutineScope.() -> Unit
): Job = lifecycleScope.launch(context, start, block)