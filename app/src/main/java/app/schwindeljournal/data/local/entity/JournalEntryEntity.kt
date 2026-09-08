package app.schwindeljournal.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import app.schwindeljournal.data.model.Ampel
import java.time.LocalDate
import java.time.LocalTime

/**
 * Superset-Schema fuer alle drei Modi (siehe datenmodell-und-content-mapping.md
 * Abschnitt 1): ein Schema, alle modus-spezifischen Felder nullable. Kein separates
 * Schema pro Modus, sonst bricht der modusuebergreifende Verlauf bei Moduswechsel.
 */
@Entity(tableName = "journal_entry")
data class JournalEntryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val datum: LocalDate,
    val uhrzeit: LocalTime,
    // Kompass/Peer/Quick Kernfeld; im Quick-Modus die einzige Freitextzeile.
    val situation: String? = null,
    // Kompass/Peer – optional/leer bei Quick.
    val kopfNackenPosition: String? = null,
    val dauerSekunden: Int? = null,
    val ampel: Ampel,
    val schlafqualitaetNachtDavor: String? = null,
    val kissenhoeheArt: String? = null,
    val tagesbewertung: String? = null,
    // NUR im Peer-Modus befuellt, sonst immer null.
    val reflexionsfrageAntwort: String? = null,
    val warnzeichenKeinesAufgetreten: Boolean = true,
)
