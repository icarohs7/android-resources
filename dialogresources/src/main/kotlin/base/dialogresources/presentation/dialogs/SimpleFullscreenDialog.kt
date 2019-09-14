package base.dialogresources.presentation.dialogs

import android.content.Context
import android.view.View

open class SimpleFullscreenDialog(
        ctx: Context,
        title: String,
        protected val content: View
) : BaseFullscreenMaterialDialog(ctx, title) {
    override fun createDialogContent(): View = content
}