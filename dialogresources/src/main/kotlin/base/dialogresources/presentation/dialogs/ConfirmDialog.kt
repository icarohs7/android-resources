package base.dialogresources.presentation.dialogs

import android.content.Context
import android.view.View
import base.dialogresources.R
import base.dialogresources.databinding.DialogConfirmBinding

class ConfirmDialog(
        ctx: Context,
        val title: String = "",
        val message: String = "",
        val cancelHandler: (() -> Unit)? = null,
        val confirmHandler: (() -> Unit)? = null
) : BaseMaterialDialog<DialogConfirmBinding>(ctx) {
    override suspend fun onCreateBinding(): Unit = with(binding) {
        title = this@ConfirmDialog.title
        message = this@ConfirmDialog.message
        cancelHandler = View.OnClickListener {
            this@ConfirmDialog.cancelHandler?.invoke()
            dialog.dismiss()
        }
        confirmHandler = View.OnClickListener {
            this@ConfirmDialog.confirmHandler?.invoke()
            dialog.dismiss()
        }
    }

    override fun getLayout(): Int {
        return R.layout.dialog_confirm
    }
}