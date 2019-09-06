package base.dialogresources.presentation.dialogs

import android.content.Context
import com.afollestad.materialdialogs.MaterialDialog

fun Context.showAlert(title: String? = null, message: String? = null): MaterialDialog {
    return MaterialDialog(this).show {
        title(text = title)
        message(text = message)
        positiveButton { }
    }
}

fun Context.showAlert(titleRes: Int? = null, messageRes: Int? = null): MaterialDialog {
    return MaterialDialog(this).show {
        titleRes?.let { title(it) }
        messageRes?.let { message(it) }
        positiveButton { }
    }
}