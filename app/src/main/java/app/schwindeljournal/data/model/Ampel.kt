package app.schwindeljournal.data.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Warning
import androidx.compose.ui.graphics.vector.ImageVector

/**
 * Schwindelbarometer (Buch Abschnitt 4.2, Spalte 5). Jede Farbe ist zusaetzlich mit
 * Symbol UND Text gekennzeichnet (UX-Leitplanke, CLAUDE.md) – nie nur Farbcodierung.
 */
enum class Ampel {
    GRUEN,
    GELB,
    ROT,
}

// sprache-Parameter defaultet auf Deutsch: bestehende Aufrufstellen ohne Argument
// (v. a. PdfZeichner, das bewusst vorerst nur Deutsch bleibt) bleiben unveraendert.
fun Ampel.anzeigename(sprache: Sprache = Sprache.DE): String =
    when (sprache) {
        Sprache.EN ->
            when (this) {
                Ampel.GRUEN -> "Green"
                Ampel.GELB -> "Yellow"
                Ampel.ROT -> "Red"
            }
        else ->
            when (this) {
                Ampel.GRUEN -> "Grün"
                Ampel.GELB -> "Gelb"
                Ampel.ROT -> "Rot"
            }
    }

/** Wortlaut 1:1 aus dem Buchmanuskript (Abschnitt 4.2), damit App und Buch konsistent bleiben. */
fun Ampel.beschreibung(sprache: Sprache = Sprache.DE): String =
    when (sprache) {
        Sprache.EN ->
            when (this) {
                Ampel.GRUEN -> "Nothing to note"
                Ampel.GELB -> "Had to briefly hold on/stop"
                Ampel.ROT -> "Only sitting/lying possible"
            }
        else ->
            when (this) {
                Ampel.GRUEN -> "Nichts zu bemerken"
                Ampel.GELB -> "Kurz festhalten/stehenbleiben müssen"
                Ampel.ROT -> "Nur Sitzen/Liegen möglich"
            }
    }

fun Ampel.icon(): ImageVector =
    when (this) {
        Ampel.GRUEN -> Icons.Filled.CheckCircle
        Ampel.GELB -> Icons.Filled.Warning
        Ampel.ROT -> Icons.Filled.Cancel
    }
