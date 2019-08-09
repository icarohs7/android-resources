package base.bottomsheetresources.domain.extensions

import base.corelibrary.domain.extensions.widthAndHeight
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.bottomsheets.setPeekHeight

fun MaterialDialog.setFullscreenPeekHeight() {
    val (_, height) = window!!.widthAndHeight()
    setPeekHeight(height)
}