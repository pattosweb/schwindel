package app.schwindeljournal.ui.journalverlauf

import app.schwindeljournal.data.local.entity.JournalEntryEntity
import app.schwindeljournal.data.local.entity.SymptomEntity
import app.schwindeljournal.data.model.Ampel
import app.schwindeljournal.data.model.Sprache
import app.schwindeljournal.data.model.SymptomTyp
import app.schwindeljournal.data.model.anzeigename
import java.time.LocalDate

data class TagesAmpel(
    val datum: LocalDate,
    val schlimmste: Ampel?,
)

/**
 * Ein rein beschreibender Haeufigkeits-Hinweis, nie als Diagnose formuliert.
 * Bewusst strukturiert statt als fertig gerenderter String: [berechneSymptomMuster]/
 * [berechneTriggerMuster] laufen in einem nicht-Composable Kontext (remember-Block
 * bzw. PDF-Export), koennen also nicht per stringResource/LocalSprache lokalisieren -
 * das Rendern in Text passiert daher erst in [MusterKarte] (UI, sprachabhaengig)
 * bzw. via [renderDe] (PDF, bleibt vorerst Deutsch).
 */
sealed interface MusterHinweis {
    data class Symptom(
        val typ: SymptomTyp,
        val anzahl: Int,
        val gesamt: Int,
    ) : MusterHinweis

    data class Trigger(
        val tag: String,
        val anzahl: Int,
        val gesamt: Int,
    ) : MusterHinweis
}

fun MusterHinweis.render(sprache: Sprache): String =
    when (this) {
        is MusterHinweis.Symptom ->
            if (sprache == Sprache.EN) {
                "${typ.anzeigename(sprache)} occurred on $anzahl of $gesamt yellow/red entries."
            } else {
                "${typ.anzeigename(sprache)} trat an $anzahl von $gesamt Gelb-/Rot-Eintraegen auf."
            }
        is MusterHinweis.Trigger ->
            if (sprache == Sprache.EN) {
                "\"$tag\" was noted on $anzahl of $gesamt yellow/red entries."
            } else {
                "\"$tag\" wurde an $anzahl von $gesamt Gelb-/Rot-Eintraegen genannt."
            }
    }

/** Fuer den (vorerst deutschsprachigen) PDF-Export, siehe [MusterHinweis]-Doku. */
fun MusterHinweis.renderDe(): String = render(Sprache.DE)

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
        .map { (typ, anzahl) -> MusterHinweis.Symptom(typ, anzahl, warnTageIds.size) }
}

/**
 * Vordefinierte Trigger-Tags, die in der Situation an Gelb-/Rot-Tagen am haeufigsten
 * genannt wurden. `triggerTags` kommt vom Aufrufer (Composable/sprachabhaengig oder
 * PDF/fest Deutsch), da diese Funktion selbst nicht lokalisieren kann (kein
 * Composable-Kontext, siehe MusterHinweis-Doku).
 */
fun berechneTriggerMuster(
    eintraege: List<JournalEntryEntity>,
    triggerTags: List<String>,
): List<MusterHinweis> {
    val warnEintraege = eintraege.filter { it.ampel != Ampel.GRUEN }
    if (warnEintraege.isEmpty()) return emptyList()
    val zaehler = mutableMapOf<String, Int>()
    warnEintraege.forEach { eintrag ->
        val situation = eintrag.situation.orEmpty().lowercase()
        triggerTags.forEach { tag ->
            if (situation.contains(tag.lowercase())) {
                zaehler[tag] = (zaehler[tag] ?: 0) + 1
            }
        }
    }
    return zaehler.entries
        .sortedByDescending { it.value }
        .take(MAX_MUSTER_HINWEISE)
        .map { (tag, anzahl) -> MusterHinweis.Trigger(tag, anzahl, warnEintraege.size) }
}
