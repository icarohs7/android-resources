package base.dialogresources.presentation.dialogs

import android.content.Context
import android.view.View
import base.dialogresources.R
import com.nikialeksey.fullscreendialog.Dialog
import com.nikialeksey.fullscreendialog.FsDialogToolbar
import com.nikialeksey.fullscreendialog.buttons.FsActionButton
import com.nikialeksey.fullscreendialog.buttons.FsCloseButton
import com.nikialeksey.fullscreendialog.buttons.SimpleButton
import splitties.resources.appDrawable

class SimpleFullscreenWithActionDialog(
        ctx: Context,
        title: String,
        private val actionText: String,
        content: View,
        private val onAction: () -> Unit
) : SimpleFullscreenDialog(ctx, title, content) {
    override fun createDialog(toolbar: FsDialogToolbar, content: View): Dialog {
        return super.createDialog(toolbar, content).apply {
            addOnAction { onAction() }
        }
    }

    override fun createToolbar(): FsDialogToolbar {
        return FsDialogToolbar(
                context,
                title,
                FsCloseButton(SimpleButton(), appDrawable(R.drawable.fs_close_icon)!!),
                FsActionButton(SimpleButton(), actionText)
        )
    }
}