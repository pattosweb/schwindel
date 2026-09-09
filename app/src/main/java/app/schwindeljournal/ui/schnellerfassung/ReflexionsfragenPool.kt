package app.schwindeljournal.ui.schnellerfassung

import app.schwindeljournal.data.model.Sprache
import java.time.LocalDate

/**
 * Reflexionsfragen-Pool fuer den Peer-Modus (roadmap-schwindeljournal-app.md,
 * Phase 4 Rest). Tonalitaet peer-nah/empathisch, Du-Ansprache, passend zum
 * "Wenn sich alles dreht"-Buch (siehe CLAUDE.md Multi-Varianten-Tabelle). Bewusst
 * keine klinischen/analytischen Fragen - das leistet bereits die Muster-Karte in
 * Kompass/Peer (strukturierte Haeufigkeitsauszaehlung).
 *
 * Eine Frage pro Kalendertag statt zufaellig bei jedem Neu-Rendern, damit sie beim
 * Wiederkehren zum selben Eintrag (z. B. nach Details auf-/zuklappen) stabil bleibt.
 */
private val reflexionsfragenPoolDe =
    listOf(
        "Was hat dir heute trotz des Schwindels gutgetan?",
        "Gab es einen Moment heute, in dem du auf dich stolz sein kannst?",
        "Was würdest du jemandem sagen, der heute genau das erlebt hat wie du?",
        "Welcher Gedanke hat dich heute am meisten begleitet?",
        "Was hättest du dir heute von anderen gewünscht?",
        "Gibt es etwas, das dir heute leichter gefallen ist, als du erwartet hattest?",
        "Wie hast du heute für dich gesorgt?",
        "Was macht dir gerade am meisten Mut?",
        "Worüber hättest du heute gern mit jemandem gesprochen?",
        "Was war heute anders als an einem \"normalen\" Schwindel-Tag?",
        "Welches kleine Erfolgserlebnis nimmst du aus dem heutigen Tag mit?",
        "Was hilft dir gerade dabei, geduldig mit dir selbst zu sein?",
        "Wann hast du dich heute am sichersten gefühlt?",
        "Was würdest du dir selbst heute gern verzeihen?",
    )

// Reihenfolge/Bedeutung 1:1 zur deutschen Liste, damit derselbe Kalendertag-Index in
// jeder Sprache zur "gleichen" Frage fuehrt.
private val reflexionsfragenPoolEn =
    listOf(
        "What did you enjoy today despite the vertigo?",
        "Was there a moment today you can be proud of?",
        "What would you tell someone who went through exactly what you did today?",
        "What thought stayed with you the most today?",
        "What would you have wished for from others today?",
        "Was there something that felt easier today than you expected?",
        "How did you take care of yourself today?",
        "What's giving you the most courage right now?",
        "What would you have liked to talk to someone about today?",
        "What was different today compared to a \"normal\" vertigo day?",
        "What small win from today do you want to hold on to?",
        "What's helping you be patient with yourself right now?",
        "When did you feel safest today?",
        "What would you like to forgive yourself for today?",
    )

fun reflexionsfrageDesTages(
    datum: LocalDate = LocalDate.now(),
    sprache: Sprache = Sprache.DE,
): String {
    val pool = if (sprache == Sprache.EN) reflexionsfragenPoolEn else reflexionsfragenPoolDe
    val index = Math.floorMod(datum.toEpochDay(), pool.size.toLong()).toInt()
    return pool[index]
}
