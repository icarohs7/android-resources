package base.dialogresources.presentation.dialogs

import android.app.DatePickerDialog
import android.content.Context
import java.util.Calendar

/**
 * @param onDateSelected Handler called when a date is selected, passing
 *      the selected year, month (from 1 to 12) and day, respectively
 */
@Suppress("FunctionName")
fun DatePickerDialog(ctx: Context, onDateSelected: (year: Int, month: Int, day: Int) -> Unit): DatePickerDialog {
    val c = Calendar.getInstance()
    val year = c.get(Calendar.YEAR)
    val month = c.get(Calendar.MONTH)
    val day = c.get(Calendar.DAY_OF_MONTH)

    return DatePickerDialog(
            ctx,
            { _, y, m, d -> onDateSelected(y, m + 1, d) },
            year,
            month,
            day
    )
}