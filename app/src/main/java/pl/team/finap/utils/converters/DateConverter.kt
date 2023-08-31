package pl.team.finap.utils.converters

import androidx.room.TypeConverter
import java.util.Date

/**
 * Utility class for all entities
 * that allows using java Date class as field
 * in a Room database entity
 * @author Kamil Cieszkowski
 */
class DateConverter {

    @TypeConverter
    fun toDate(timestamp: Long?): Date? {
        return timestamp?.let { Date(it) }
    }

    @TypeConverter
    fun toTimestamp(date: Date?): Long? {
        return date?.time?.toLong()
    }
}