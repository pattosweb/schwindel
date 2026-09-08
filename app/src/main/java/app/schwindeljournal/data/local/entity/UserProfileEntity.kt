package app.schwindeljournal.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import app.schwindeljournal.data.model.Modus
import java.time.LocalDate
import java.time.LocalTime

/**
 * Superset-Schema des Steckbriefs (Buch Abschnitt 4.1), siehe
 * datenmodell-und-content-mapping.md Abschnitt 1. Single-User-App: Es existiert
 * ueber die App-Laufzeit hinweg genau eine Zeile in dieser Tabelle. Die id ist
 * bewusst fest auf [SINGLETON_ID] gesetzt (statt autoGenerate) – zusammen mit
 * OnConflictStrategy.IGNORE in UserProfileDao.insert() macht das ein doppeltes
 * Anlegen (z. B. Doppel-Tap auf "Los geht's" bei eingeschraenkter Motorik waehrend
 * eines Schwindelanfalls, oder ein Retry nach App-Absturz mitten im Insert)
 * unschaedlich, statt eine inkonsistente zweite Profilzeile zu erzeugen.
 */
@Entity(tableName = "user_profile")
data class UserProfileEntity(
    @PrimaryKey
    val id: Long = SINGLETON_ID,
    val modus: Modus,
    val geburtsjahr: Int? = null,
    val berufMitBelastung: Boolean? = null,
    val vorerkrankungen: String? = null,
    val fruehereVerletzungenKopfNacken: String? = null,
    val ersterVorfall: Boolean? = null,
    val seitWannWiederkehrend: String? = null,
    val letzterBlutdruck: String? = null,
    val letzterBlutdruckDatum: LocalDate? = null,
    // Nur im Quick-Modus relevant: Standard-Journal-Fenster ist 30 Tage, hier auf
    // 60 Tage erweiterbar (datenmodell-und-content-mapping.md Abschnitt 2). null/false
    // = Standardfenster.
    val journalFensterErweitert: Boolean? = null,
    // Phase 4: taegliche Erinnerung (zentral ueber WorkManager, siehe ReminderScheduler).
    val reminderAktiviert: Boolean? = null,
    val reminderUhrzeit: LocalTime? = null,
    // Phase 4: Barrierefreiheit - kontrastreichere, bei Rot-Gruen-Sehschwaeche besser
    // unterscheidbare Ampel-Palette (siehe ui/theme/Color.kt). null/false = Standard.
    val ampelHoherKontrast: Boolean? = null,
) {
    companion object {
        const val SINGLETON_ID: Long = 1L
    }
}
