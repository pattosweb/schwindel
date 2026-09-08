package app.schwindeljournal.ui.journalverlauf

import app.schwindeljournal.data.local.entity.JournalEntryEntity
import app.schwindeljournal.data.local.entity.SymptomEntity
import app.schwindeljournal.data.model.Ampel
import app.schwindeljournal.data.model.anzeigename
import app.schwindeljournal.ui.components.vordefinierteTriggerTags
import java.time.LocalDate

data class TagesAmpel(
    val datum: LocalDate,
    val schlimmste: Ampel?,
)

/** Ein rein beschreibender Haeufigkeits-Hinweis, nie als Diagnose formuliert. */
data class MusterHinweis(
    val text: String,
)

private fun ampelSchwere(ampel: Ampel): Int =
    when (ampel) {
        Ampel.GRUEN -> 0
        Ampel.GELB -> 1
        Ampel.ROT -> 2
    }

/** Ein Balken pro Tag im Fenster, auch fuer Tage ohne Eintrag (schlimmste = null). */
fun berechneTagesAmpel(
    eintraege: List<JournalEntryEntity>,
    fensterTage: Int,
): List<TagesAmpel> {
    val heute = LocalDate.now()
    val start = heute.minusDays((fensterTage - 1).toLong())
    val proTag =
        eintraege
            .filter { it.datum >= start && it.datum <= heute }
            .groupBy { it.datum }
            .mapValues { (_, tagesEintraege) -> tagesEintraege.map { it.ampel }.maxByOrNull(::ampelSchwere) }
    return (0 until fensterTage).map { offset ->
        val tag = start.plusDays(offset.toLong())
        TagesAmpel(tag, proTag[tag])
    }
}

private const val MAX_MUSTER_HINWEISE = 3

/** Begleitsymptome, die an Gelb-/Rot-Tagen am haeufigsten vorkamen (nur strukturierte Daten). */
fun berechneSymptomMuster(
    eintraege: List<JournalEntryEntity>,
    symptome: List<SymptomEntity>,
): List<MusterHinweis> {
    val warnTageIds = eintraege.filter { it.ampel != Ampel.GRUEN }.map { it.id }.toSet()
    if (warnTageIds.isEmpty()) return emptyList()
    val haeufigkeit = symptome.filter { it.entryId in warnTageIds }.groupingBy { it.typ }.eachCount()
    return haeufigkeit.entries
        .sortedByDescending { it.value }
        .take(MAX_MUSTER_HINWEISE)
        .map { (typ, anzahl) ->
            MusterHinweis("${typ.anzeigename()} trat an $anzahl von ${warnTageIds.size} Gelb-/Rot-Eintraegen auf.")
        }
}

/** Vordefinierte Trigger-Tags, die in der Situation an Gelb-/Rot-Tagen am haeufigsten genannt wurden. */
fun berechneTriggerMuster(eintraege: List<JournalEntryEntity>): List<MusterHinweis> {
    val warnEintraege = eintraege.filter { it.ampel != Ampel.GRUEN }
    if (warnEintraege.isEmpty()) return emptyList()
    val zaehler = mutableMapOf<String, Int>()
    warnEintraege.forEach { eintrag ->
        val situation = eintrag.situation.orEmpty().lowercase()
        vordefinierteTriggerTags.forEach { tag ->
            if (situation.contains(tag.lowercase())) {
                zaehler[tag] = (zaehler[tag] ?: 0) + 1
            }
        }
    }
    return zaehler.entries
        .sortedByDescending { it.value }
        .take(MAX_MUSTER_HINWEISE)
        .map { (tag, anzahl) ->
            MusterHinweis("\"$tag\" wurde an $anzahl von ${warnEintraege.size} Gelb-/Rot-Eintraegen genannt.")
        }
}
