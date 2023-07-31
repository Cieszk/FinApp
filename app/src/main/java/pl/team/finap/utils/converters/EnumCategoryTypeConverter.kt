package pl.team.finap.utils.converters

import androidx.room.TypeConverter
import pl.team.finap.database.entities.CategoryType

/**
 * Utility class for Transaction Entity, which allows
 * to use enum as a field in a Room entity, since
 * the Room itself doesn't support enum type directly
 * @author Kamil Cieszkowski
 */
class EnumCategoryTypeConverter {

    @TypeConverter
    fun toCategoryType(value: String) = enumValueOf<CategoryType>(value)

    @TypeConverter
    fun fromCategoryType(value: CategoryType) = value.name
}