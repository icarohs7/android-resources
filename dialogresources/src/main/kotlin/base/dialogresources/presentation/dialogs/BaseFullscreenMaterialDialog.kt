package base.dialogresources.presentation.dialogs

import android.content.Context
import android.util.Log
import android.view.View
import base.dialogresources.R
import base.dialogresources.domain.toolbar
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
import splitties.resources.appColor
import splitties.resources.appDrawable
import splitties.views.backgroundColor

abstract class BaseFullscreenMaterialDialog(protected val context: Context, protected val title: String) {
    val lifecycleScope: CoroutineScope = MainScope()
    val dialog by lazy {
        val toolbar = createToolbar()
        val content = createDialogContent()
        createDialog(toolbar, content).apply {
            if (this is DismissOnCloseDialog) addOnClose { lifecycleScope.job.cancelChildren() }
            else addOnAction { lifecycleScope.job.cancelChildren() }
            onCreateView(toolbar, content)
        }
    }

    open fun onCreateView(toolbar: FsDialogToolbar, view: View) {
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
        ).apply {
            backgroundColor = appColor(R.color.colorPrimaryVariant)
            toolbar.setTitleTextColor(appColor(R.color.colorOnPrimary))
        }
    }

    fun show() {
        GlobalScope.launch(Dispatchers.Main) { dialog.show() }
    }

    fun dismiss() {
        GlobalScope.launch(Dispatchers.Main) { dialog.dismiss() }
    }

    abstract fun createDialogContent(): View
}