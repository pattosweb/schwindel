package app.schwindeljournal.data.local.converter

import androidx.room.TypeConverter
import app.schwindeljournal.data.model.Sprache

/**
 * Eigene Datei statt in [Converters] (Kohaesion + TooManyFunctions), analog zu
 * [DateTimeConverters]. Mehrsprachigkeit (Phase 5): sprache-Spalten in ContentBlock
 * (nicht nullable) und UserProfile (nullable = "folge Systemsprache").
 */
class SpracheConverter {
    @TypeConverter
    fun fromSprache(value: Sprache): String = value.name

    @TypeConverter
    fun toSprache(value: String): Sprache = Sprache.valueOf(value)

    @TypeConverter
    fun fromSpracheNullable(value: Sprache?): String? = value?.name

    @TypeConverter
    fun toSpracheNullable(value: String?): Sprache? = value?.let(Sprache::valueOf)
}
