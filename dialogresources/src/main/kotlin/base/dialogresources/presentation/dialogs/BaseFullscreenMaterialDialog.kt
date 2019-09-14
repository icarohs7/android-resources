package base.dialogresources.presentation.dialogs

import android.content.Context
import android.view.View
import base.dialogresources.R
import com.github.icarohs7.unoxcore.extensions.coroutines.job
import com.nikialeksey.fullscreendialog.Dialog
import com.nikialeksey.fullscreendialog.DismissOnCloseDialog
import com.nikialeksey.fullscreendialog.FsDialog
import com.nikialeksey.fullscreendialog.FsDialogToolbar
import com.nikialeksey.fullscreendialog.buttons.FsActionButton
import com.nikialeksey.fullscreendialog.buttons.FsCloseButton
import com.nikialeksey.fullscreendialog.buttons.SimpleButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.launch
import splitties.resources.appDrawable

abstract class BaseFullscreenMaterialDialog(protected val context: Context, protected val title: String) {
    val lifecycleScope: CoroutineScope = MainScope()
    val dialog by lazy {
        createDialog(createToolbar(), createDialogContent()).apply {
            if (this is DismissOnCloseDialog) addOnClose { lifecycleScope.job.cancelChildren() }
            else addOnAction { lifecycleScope.job.cancelChildren() }
        }
    }

    open fun createDialog(toolbar: FsDialogToolbar, content: View): Dialog {
        return DismissOnCloseDialog(FsDialog(
                context,
                R.style.AppTheme,
                toolbar,
                content
        ))
    }

    open fun createToolbar(): FsDialogToolbar {
        return FsDialogToolbar(
                context,
                title,
                FsCloseButton(SimpleButton(), appDrawable(R.drawable.fs_close_icon)!!),
                FsActionButton(SimpleButton(), "")
        )
    }

    fun show() {
        GlobalScope.launch(Dispatchers.Main) { dialog.show() }
    }

    fun dismiss() {
        GlobalScope.launch(Dispatchers.Main) { dialog.dismiss() }
    }

    abstract fun createDialogContent(): View
}