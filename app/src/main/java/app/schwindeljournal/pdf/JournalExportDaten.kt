package app.schwindeljournal.pdf

import app.schwindeljournal.data.local.entity.AnsprechpartnerEntity
import app.schwindeljournal.data.local.entity.JournalEntryEntity
import app.schwindeljournal.data.local.entity.MedikamentEntity
import app.schwindeljournal.data.local.entity.SymptomEntity
import app.schwindeljournal.data.local.entity.UserProfileEntity
import app.schwindeljournal.ui.journalverlauf.MusterHinweis

/** Buendelt alle Daten fuer einen PDF-Export in einem Wert statt einer langen Parameterliste. */
data class JournalExportDaten(
    val profil: UserProfileEntity,
    val medikamente: List<MedikamentEntity>,
    val ansprechpartner: List<AnsprechpartnerEntity>,
    val eintraege: List<JournalEntryEntity>,
    val symptome: List<SymptomEntity>,
    val musterHinweise: List<MusterHinweis>,
)
