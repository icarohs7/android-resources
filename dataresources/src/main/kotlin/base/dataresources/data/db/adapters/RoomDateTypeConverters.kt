package base.dataresources.data.db.adapters

import androidx.room.TypeConverter
import java.util.Date

/**
 * Type converters transforming date
 * related types
 */
class RoomDateTypeConverters {
    @TypeConverter
    fun fromDate(value: Date?): Long {
        return value?.time ?: -1
    }

    @TypeConverter
    fun toDate(value: Long): Date? {
        return if (value < 0) null else Date(value)
    }
}