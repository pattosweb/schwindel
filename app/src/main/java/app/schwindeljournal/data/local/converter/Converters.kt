package app.schwindeljournal.data.local.converter

import androidx.room.TypeConverter
import app.schwindeljournal.data.model.Ampel
import app.schwindeljournal.data.model.Modus
import app.schwindeljournal.data.model.SymptomTyp
import java.time.LocalDate
import java.time.LocalTime

class Converters {
    @TypeConverter
    fun fromLocalDate(value: LocalDate?): String? = value?.toString()

    @TypeConverter
    fun toLocalDate(value: String?): LocalDate? = value?.let(LocalDate::parse)

    @TypeConverter
    fun fromLocalTime(value: LocalTime?): String? = value?.toString()

    @TypeConverter
    fun toLocalTime(value: String?): LocalTime? = value?.let(LocalTime::parse)

    @TypeConverter
    fun fromModus(value: Modus): String = value.name

    @TypeConverter
    fun toModus(value: String): Modus = Modus.valueOf(value)

    @TypeConverter
    fun fromAmpel(value: Ampel): String = value.name

    @TypeConverter
    fun toAmpel(value: String): Ampel = Ampel.valueOf(value)

    @TypeConverter
    fun fromSymptomTyp(value: SymptomTyp): String = value.name

    @TypeConverter
    fun toSymptomTyp(value: String): SymptomTyp = SymptomTyp.valueOf(value)
}
