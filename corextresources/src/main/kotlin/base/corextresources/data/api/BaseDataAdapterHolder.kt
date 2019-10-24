package base.corextresources.data.api

import base.coreresources.extensions.asDate
import base.coreresources.extensions.asRemoteDate
import com.github.icarohs7.unoxcore.extensions.orZero

interface BaseDataAdapterHolder {
    fun int(s: String): Int = s.toIntOrNull().orZero()
    fun int(b: Boolean): Int = if (b) 1 else 0
    fun double(s: String): Double = s.toDoubleOrNull().orZero()
    fun boolean(i: Int): Boolean = i != 0
    fun dateStr(s: String, format: String = "dd/MM/yyyy HH:mm"): String = s.asDate(format).asRemoteDate

    /**
     * Strip html tags from string,
     * also replace `null` values
     * with empty strings
     */
    fun String?.removeHtmlAndNull(): String {
        return this
            .orEmpty()
            .replace("&lt;", "<")
            .replace("&gt;", ">")
            .replace("<br>", "\n")
            .replace("<br/>", "\n")
            .replace("<br />", "\n")
            .replace(Regex("<[^>]*>"), "")
            .replace(Regex("^null$"), "")
    }
}