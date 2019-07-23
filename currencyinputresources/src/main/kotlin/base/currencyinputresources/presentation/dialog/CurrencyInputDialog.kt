package base.currencyinputresources.presentation.dialog

import android.content.Context
import base.currencyinputresources.R
import base.currencyinputresources.databinding.DialogCurrencyInputBinding
import base.dialogresources.presentation.dialogs.BaseMaterialDialog

class CurrencyInputDialog(
        ctx: Context,
        private val title: String = "",
        private val hint: String = "",
        private val confirmHandler: CurrencyInputDialog.(currencyValue: Long) -> Unit
) : BaseMaterialDialog<DialogCurrencyInputBinding>(ctx) {
    override suspend fun onCreateBinding() {
        setupBinding()
    }

    private fun setupBinding(): Unit = with(binding) {
        title = this@CurrencyInputDialog.title
        hint = this@CurrencyInputDialog.hint
        setConfirmHandler { this@CurrencyInputDialog.confirmHandler(editInput.rawValue) }
    }

    override fun getLayout(): Int {
        return R.layout.dialog_currency_input
    }
}