package base.dialogresources.presentation.dialogs

import android.content.Context
import dmax.dialog.SpotsDialog

@Suppress("FunctionName")
fun LoadingDialog(ctx: Context, message: String): SpotsDialog {
    return SpotsDialog
            .Builder()
            .setContext(ctx)
            .setMessage(message)
            .build() as SpotsDialog
}