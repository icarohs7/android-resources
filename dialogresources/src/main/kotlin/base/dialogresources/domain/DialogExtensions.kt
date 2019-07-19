package base.dialogresources.domain

import android.app.Dialog

suspend fun <D : Dialog, T> D.showWhileRunning(operation: suspend D.() -> T): T {
    return try {
        show()
        operation()
    } finally {
        dismiss()
    }
}