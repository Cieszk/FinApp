package pl.team.finap.utils.converters

import androidx.room.TypeConverter
import java.math.BigDecimal

object BigDecimalConverters {
    @TypeConverter
    @JvmStatic
    fun fromBigDecimal(value: BigDecimal?): String? {
        return value?.toString()
    }

    @TypeConverter
    @JvmStatic
    fun toBigDecimal(value: String?): BigDecimal? {
        return if (value == null) null else BigDecimal(value)
    }
}