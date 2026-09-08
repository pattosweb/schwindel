package app.schwindeljournal.data.local.converter

import androidx.room.TypeConverter
import app.schwindeljournal.data.model.Ampel
import app.schwindeljournal.data.model.BuchTeil
import app.schwindeljournal.data.model.Modus
import app.schwindeljournal.data.model.SymptomTyp
import app.schwindeljournal.data.model.VariantenTiefe

/** Enum-Konverter, getrennt von [DateTimeConverters] (Kohaesion + TooManyFunctions). */
class Converters {
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

    @TypeConverter
    fun fromBuchTeil(value: BuchTeil): String = value.name

    @TypeConverter
    fun toBuchTeil(value: String): BuchTeil = BuchTeil.valueOf(value)

    @TypeConverter
    fun fromVariantenTiefe(value: VariantenTiefe): String = value.name

    @TypeConverter
    fun toVariantenTiefe(value: String): VariantenTiefe = VariantenTiefe.valueOf(value)

    @TypeConverter
    fun fromModusSet(value: Set<Modus>): String = value.joinToString(",") { it.name }

    @TypeConverter
    fun toModusSet(value: String): Set<Modus> =
        value
            .split(",")
            .filter { it.isNotBlank() }
            .map(Modus::valueOf)
            .toSet()
}
