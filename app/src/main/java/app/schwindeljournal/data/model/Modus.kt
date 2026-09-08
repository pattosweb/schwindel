package app.schwindeljournal.data.model

/**
 * Der gewaehlte App-Modus, entspricht einer der drei Buchvarianten
 * (siehe CLAUDE.md, Abschnitt "Multi-Varianten-Architektur").
 * Ein Moduswechsel aendert nur Anzeige-/Formularumfang, niemals das Schema.
 */
enum class Modus {
    KOMPASS,
    PEER,
    QUICK,
}

fun Modus.anzeigename(): String =
    when (this) {
        Modus.KOMPASS -> "Kompass"
        Modus.PEER -> "Peer"
        Modus.QUICK -> "Quick"
    }

fun Modus.buchtitel(): String =
    when (this) {
        Modus.KOMPASS -> "\"Mein Schwindel-Kompass\""
        Modus.PEER -> "\"Wenn sich alles dreht\""
        Modus.QUICK -> "\"Schwindel im Blick\""
    }

fun Modus.beschreibung(): String =
    when (this) {
        Modus.KOMPASS ->
            "Voller Eintrag mit Steckbrief, sachlich-systematisch – für alle, die volle " +
                "Übersicht wollen."
        Modus.PEER ->
            "Wie Kompass, zusätzlich täglich eine kurze Reflexionsfrage – persönlich, " +
                "von Betroffenen für Betroffene erzählt."
        Modus.QUICK ->
            "Nur die wichtigsten Angaben in wenigen Sekunden erfasst – knapp und " +
                "checklistenartig."
    }
