package base.dialogresources.domain

import android.app.Dialog
import base.dialogresources.presentation.dialogs.BaseMaterialDialog
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