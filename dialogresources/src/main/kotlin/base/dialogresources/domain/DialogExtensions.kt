package base.dialogresources.domain

import android.app.Dialog

inline fun <D : Dialog, T> D.showWhileRunning(operation: D.() -> T): T {
    return try {
        show()
        operation()
    } finally {
        dismiss()
    }
}