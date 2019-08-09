package base.bottomsheetresources.presentation.dialogs

import android.content.Context
import androidx.databinding.ViewDataBinding
import base.bottomsheetresources.domain.extensions.setFullscreenPeekHeight
import base.dialogresources.presentation.dialogs.BaseMaterialDialog
import com.afollestad.materialdialogs.LayoutMode
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.bottomsheets.BottomSheet

/**
 * Base class used to hold and handle
 * fullscreen bottom sheets
 */
abstract class BaseFullscreenMaterialBottomSheet<T : ViewDataBinding>(ctx: Context) : BaseMaterialDialog<T>(ctx) {
    override fun onShow() {
        super.onShow()
        dialog.setFullscreenPeekHeight()
    }

    override fun getMaterialDialog(): MaterialDialog {
        return MaterialDialog(context, BottomSheet(LayoutMode.MATCH_PARENT))
    }
}