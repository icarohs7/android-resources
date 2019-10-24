package base.dialogresources.presentation.dialogs

import android.content.Context
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.list.SingleChoiceListener
import com.afollestad.materialdialogs.list.listItemsSingleChoice

fun SelectListItemDialog(
    ctx: Context,
    title: String = "",
    items: List<String>,
    onItemSelect: SingleChoiceListener
): MaterialDialog {
    return MaterialDialog(ctx).apply {
        title(text = title)
        listItemsSingleChoice(items = items, selection = onItemSelect)
    }
}