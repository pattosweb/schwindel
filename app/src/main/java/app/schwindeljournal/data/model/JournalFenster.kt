package app.schwindeljournal.data.model

/**
 * Standard-Journal-Dauer (datenmodell-und-content-mapping.md Abschnitt 2): Kompass/
 * Peer immer 60 Tage, Quick standardmaessig 30 Tage, in den Einstellungen auf 60
 * erweiterbar.
 */
private const val QUICK_STANDARD_FENSTER_TAGE = 30
private const val STANDARD_FENSTER_TAGE = 60

fun journalFensterTage(
    modus: Modus,
    erweitert: Boolean?,
): Int = if (modus == Modus.QUICK && erweitert != true) QUICK_STANDARD_FENSTER_TAGE else STANDARD_FENSTER_TAGE
