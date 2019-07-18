package base.dialogresources.presentation.dialogs

import android.app.TimePickerDialog
import android.content.Context
import android.text.format.DateFormat
import java.util.Calendar

/**
 * @param onTimeSelected Handler called when a time is selected, passing
 *      the selected hour and minute, respectively
 */
@Suppress("FunctionName")
fun TimePickerDialog(ctx: Context, onTimeSelected: (hour: Int, minute: Int) -> Unit): TimePickerDialog {
    val c = Calendar.getInstance()
    val hour = c.get(Calendar.HOUR_OF_DAY)
    val minute = c.get(Calendar.MINUTE)

    return TimePickerDialog(
            ctx,
            { _, h, m -> onTimeSelected(h, m) },
            hour,
            minute,
            DateFormat.is24HourFormat(ctx)
    )
}