package app.schwindeljournal.ui.schnellerfassung

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
private val reflexionsfragenPool =
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

fun reflexionsfrageDesTages(datum: LocalDate = LocalDate.now()): String {
    val index = Math.floorMod(datum.toEpochDay(), reflexionsfragenPool.size.toLong()).toInt()
    return reflexionsfragenPool[index]
}
