package base.dialogresources.presentation.dialogs

import android.content.Context
import base.corelibrary.R
import splitties.resources.appColor
import splitties.resources.appStr
import splitties.views.backgroundColor

@Suppress("FunctionName")
fun ConfirmDisconnectDialog(ctx: Context, confirmHandler: () -> Unit) {
    val dialog = ConfirmDialog(
            ctx,
            title = "Confirmar",
            message = "Deseja se desconectar?",
            confirmHandler = confirmHandler
    )
    dialog.binding.apply {
        btnConfirm.text = appStr(R.string.sim)
        btnCancel.text = appStr(R.string.nao)
        txtTitle.backgroundColor = appColor(R.color.colorPrimary)
    }
    dialog.show()
}