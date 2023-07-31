package pl.team.finap.utils.converters

import androidx.room.TypeConverter
import pl.team.finap.database.entities.TransactionType

/**
 * Utility class for Transaction Entity, which allows
 * to use enum as a field in a Room entity, since
 * the Room itself doesn't support enum type directly
 * @author Kamil Cieszkowski
 */
class EnumTransactionTypeConverter {
    @TypeConverter
    fun toTransactionType(value: String) = enumValueOf<TransactionType>(value)

    @TypeConverter
    fun fromTransactionType(value: TransactionType) = value.name
}