package app.schwindeljournal.data.model

/**
 * Mehrsprachigkeit (Roadmap "Bewusst zurückgestellt" → jetzt aktiv). DE ist die
 * Ursprungssprache der drei Buchvarianten und bleibt die Basis-/Fallback-Sprache
 * (values/strings.xml ohne Sprach-Suffix). Weitere Sprachen werden schrittweise
 * ergaenzt (Grundgeruest+Englisch zuerst) - [verfuegbareSprachen] steuert, welche
 * in der Einstellungen-Auswahl bereits erscheinen, damit keine halbfertige
 * Sprache (gemischt Deutsch/Zielsprache) sichtbar wird.
 */
enum class Sprache {
    DE,
    EN,
    PL,
    RU,
    FR,
    TR,
    AR,
}

fun Sprache.code(): String =
    when (this) {
        Sprache.DE -> "de"
        Sprache.EN -> "en"
        Sprache.PL -> "pl"
        Sprache.RU -> "ru"
        Sprache.FR -> "fr"
        Sprache.TR -> "tr"
        Sprache.AR -> "ar"
    }

/** Eigenbezeichnung in der jeweiligen Sprache selbst (nicht uebersetzt) fuer den Sprachwaehler. */
fun Sprache.eigenname(): String =
    when (this) {
        Sprache.DE -> "Deutsch"
        Sprache.EN -> "English"
        Sprache.PL -> "Polski"
        Sprache.RU -> "Русский"
        Sprache.FR -> "Français"
        Sprache.TR -> "Türkçe"
        Sprache.AR -> "العربية"
    }

fun Sprache.istRtl(): Boolean = this == Sprache.AR

/**
 * Sprachen mit vollstaendiger Uebersetzung (UI + kompletter Buchinhalt), waehlbar in
 * den Einstellungen. Wird erweitert, sobald eine weitere Sprache fertig ist -
 * absichtlich keine automatische Freischaltung ueber blosse Teil-Uebersetzung.
 */
val verfuegbareSprachen = setOf(Sprache.DE, Sprache.EN)

/**
 * Effektive Sprache: expliziter Nutzerwunsch hat Vorrang, sonst Systemsprache (falls
 * verfuegbar), sonst Deutsch als Ursprungssprache der Buecher.
 */
fun resolveEffektiveSprache(
    gewaehlt: Sprache?,
    systemSprachCode: String,
): Sprache {
    if (gewaehlt != null && gewaehlt in verfuegbareSprachen) return gewaehlt
    return Sprache.entries.firstOrNull { it in verfuegbareSprachen && it.code() == systemSprachCode } ?: Sprache.DE
}
