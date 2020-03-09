package base.dataresources.data.db.adapters

import androidx.room.TypeConverter
import base.corextresources.domain.toplevel.NXJson
import kotlinx.serialization.builtins.list
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.json.Json

/**
 * Type converters using json format
 * as serializer and serializer for
 * a given type, use as the last
 * converter
 */
class RoomJsonTypeConverters {
    @TypeConverter
    fun fromListString(data: List<String>): String {
        return NXJson.stringify(String.serializer().list, data)
    }

    @TypeConverter
    fun toListString(data: String): List<String> {
        return NXJson.parse(String.serializer().list, data)
    }
}