package base.dialogresources.presentation.dialogs

import android.content.Context
import base.dialogresources.R
import base.dialogresources.databinding.DialogConfirmBinding
import splitties.views.onClick

class ConfirmDialog(
    ctx: Context,
    val title: String = "",
    val message: String = "",
    val cancelHandler: (() -> Unit)? = null,
    val confirmHandler: (() -> Unit)? = null
) : BaseMaterialDialog<DialogConfirmBinding>(ctx) {
    override suspend fun onCreateBinding(): Unit = with(binding) {
        txtTitle.text = title
        txtMessage.text = message
        btnCancel.onClick {
            cancelHandler?.invoke()
            dialog.dismiss()
        }
        btnConfirm.onClick {
            confirmHandler?.invoke()
            dialog.dismiss()
        }
    }

    override fun getLayout(): Int {
        return R.layout.dialog_confirm
    }
}