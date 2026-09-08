package app.schwindeljournal.data.model

/**
 * Content-Tiefe je ContentBlock-Zeile (datenmodell-und-content-mapping.md Abschnitt 1).
 * KOMPASS zeigt VOLL, PEER zeigt PEER, QUICK zeigt KURZ - Ausnahme: Warnzeichen-
 * Inhalte (istWarnzeichenInhalt=true) werden unabhaengig davon in allen Modi gezeigt.
 */
enum class VariantenTiefe { VOLL, PEER, KURZ }
