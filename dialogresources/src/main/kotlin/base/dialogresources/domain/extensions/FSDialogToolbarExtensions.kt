package base.dialogresources.domain.extensions

import androidx.appcompat.widget.Toolbar
import androidx.core.view.children
import com.nikialeksey.fullscreendialog.FsDialogToolbar


val FsDialogToolbar.toolbar: Toolbar
    get() = children.first() as Toolbar