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
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }
}