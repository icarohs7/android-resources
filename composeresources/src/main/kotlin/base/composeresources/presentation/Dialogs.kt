package base.composeresources.presentation

import android.content.Context
import androidx.compose.Composable
import androidx.compose.FrameLayout
import androidx.ui.core.*
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView

@Composable fun ComposeDialog(context: Context, child: @Composable() (dialog: MaterialDialog) -> Unit): MaterialDialog {
    val root = FrameLayout(context)
    val dialog = MaterialDialog(context).customView(view = root, noVerticalPadding = true)
    root.setContent { child(dialog) }
    return dialog
}