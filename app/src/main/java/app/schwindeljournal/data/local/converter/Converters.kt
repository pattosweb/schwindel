package app.schwindeljournal.data.local.converter

import androidx.room.TypeConverter
import app.schwindeljournal.data.model.Modus
import java.time.LocalDate

class Converters {
    @TypeConverter
    fun fromLocalDate(value: LocalDate?): String? = value?.toString()

    @TypeConverter
    fun toLocalDate(value: String?): LocalDate? = value?.let(LocalDate::parse)

    @TypeConverter
    fun fromModus(value: Modus): String = value.name

    @TypeConverter
    fun toModus(value: String): Modus = Modus.valueOf(value)
}
