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

fun Modus.anzeigename(sprache: Sprache = Sprache.DE): String =
    when (sprache) {
        Sprache.EN ->
            when (this) {
                Modus.KOMPASS -> "Compass"
                Modus.PEER -> "Peer"
                Modus.QUICK -> "Quick"
            }
        else ->
            when (this) {
                Modus.KOMPASS -> "Kompass"
                Modus.PEER -> "Peer"
                Modus.QUICK -> "Quick"
            }
    }

fun Modus.buchtitel(sprache: Sprache = Sprache.DE): String =
    when (sprache) {
        Sprache.EN ->
            when (this) {
                Modus.KOMPASS -> "\"My Vertigo Compass\""
                Modus.PEER -> "\"When Everything Spins\""
                Modus.QUICK -> "\"Vertigo at a Glance\""
            }
        else ->
            when (this) {
                Modus.KOMPASS -> "\"Mein Schwindel-Kompass\""
                Modus.PEER -> "\"Wenn sich alles dreht\""
                Modus.QUICK -> "\"Schwindel im Blick\""
            }
    }

fun Modus.beschreibung(sprache: Sprache = Sprache.DE): String =
    when (sprache) {
        Sprache.EN ->
            when (this) {
                Modus.KOMPASS ->
                    "Full entry with profile, factual and systematic – for anyone who wants " +
                        "the complete picture."
                Modus.PEER ->
                    "Like Compass, plus a short daily reflection question – personal, told by " +
                        "someone who's been there for someone going through it."
                Modus.QUICK ->
                    "Just the essentials, captured in a few seconds – brief and checklist-style."
            }
        else ->
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
    }
