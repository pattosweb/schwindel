package app.schwindeljournal.ui.uebungsbegleiter

import app.schwindeljournal.data.model.Sprache

// Eigene Datei statt in UebungsBegleiterScreen.kt (detekt TooManyFunctions), analog
// zum Converters/DateTimeConverters-Split.
internal fun nurVollePrivilegierteModiText(sprache: Sprache): String =
    when (sprache) {
        Sprache.EN ->
            "Full exercises with a timer are available in Compass or Peer mode. " +
                "Warning signs are in the Knowledge Library."
        else ->
            "Vollständige Übungen mit Timer sind im Kompass- oder Peer-Modus " +
                "verfügbar. Warnzeichen findest du in der Wissens-Bibliothek."
    }

internal fun ladeUebungenText(sprache: Sprache): String =
    when (sprache) {
        Sprache.EN -> "Loading exercises …"
        else -> "Übungen werden geladen …"
    }

internal fun zurueckBeschreibung(sprache: Sprache): String =
    if (sprache == Sprache.EN) "Back to exercise list" else "Zurück zur Übungsliste"
