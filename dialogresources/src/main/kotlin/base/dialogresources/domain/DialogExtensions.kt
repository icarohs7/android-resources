package base.dialogresources.domain

import android.app.Dialog
import base.dialogresources.presentation.dialogs.BaseMaterialDialog
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

inline fun <D : Dialog, T> D.showWhileRunning(operation: D.() -> T): T {
    return try {
        show()
        operation()
    } finally {
        dismiss()
    }
}

inline fun <T : BaseMaterialDialog<*>> T.show(block: T.() -> Unit): T {
    block()
    show()
    return this
}

suspend inline fun BaseMaterialDialog<*>.awaitDismiss(): Unit = dialog.awaitDismiss()
suspend fun <D : Dialog> D.awaitDismiss() {
    suspendCoroutine<Unit> { cont ->
        setOnDismissListener { cont.resume(Unit) }
    }
}

suspend inline fun BaseMaterialDialog<*>.showAndAwaitDismiss(): Unit = dialog.showAndAwaitDismiss()
suspend fun <D : Dialog> D.showAndAwaitDismiss() {
    suspendCoroutine<Unit> { cont ->
        setOnDismissListener { cont.resume(Unit) }
        show()
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