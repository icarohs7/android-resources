package base.coreresources.extensions

import java.text.NumberFormat
import java.util.Locale

//TODO move to unox-core
fun Double.localizedFormat(locale: Locale = Locale.getDefault()): String {
    return NumberFormat.getInstance(locale).format(this)
}