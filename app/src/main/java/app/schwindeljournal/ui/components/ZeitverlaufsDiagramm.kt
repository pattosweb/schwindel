package app.schwindeljournal.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import app.schwindeljournal.data.model.Ampel
import app.schwindeljournal.data.model.anzeigename
import app.schwindeljournal.data.model.icon
import app.schwindeljournal.ui.shared.LocalSprache
import app.schwindeljournal.ui.theme.AmpelGelb
import app.schwindeljournal.ui.theme.AmpelGelbKontrast
import app.schwindeljournal.ui.theme.AmpelGruen
import app.schwindeljournal.ui.theme.AmpelGruenKontrast
import app.schwindeljournal.ui.theme.AmpelRot
import app.schwindeljournal.ui.theme.AmpelRotKontrast

private const val LEERER_TAG_HOEHE_ANTEIL = 0.15f
private const val BALKEN_BREITE_ANTEIL = 0.8f

/**
 * hoherKontrast: Barrierefreiheits-Einstellung (Einstellungen-Screen, UserProfile.
 * ampelHoherKontrast) - ersetzt Gruen/Rot durch eine bei Rot-Gruen-Sehschwaeche
 * besser unterscheidbare Palette, siehe Color.kt-Kommentar.
 */
fun ampelFarbe(
    ampel: Ampel,
    hoherKontrast: Boolean = false,
): Color =
    when (ampel) {
        Ampel.GRUEN -> if (hoherKontrast) AmpelGruenKontrast else AmpelGruen
        Ampel.GELB -> if (hoherKontrast) AmpelGelbKontrast else AmpelGelb
        Ampel.ROT -> if (hoherKontrast) AmpelRotKontrast else AmpelRot
    }

/**
 * Ampelfarbe pro Tag als einfache Balkenreihe – keine Animation, keine Interaktion
 * (UX-Leitplanke). Tage ohne Eintrag bleiben als heller Umriss erkennbar. Die
 * Legende darunter gibt jeder Farbe zusaetzlich Symbol+Text (nie nur Farbcodierung).
 */
@Composable
fun ZeitverlaufsDiagramm(
    tage: List<TagesAmpelUi>,
    hoherKontrast: Boolean = false,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        Canvas(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .height(64.dp),
        ) {
            if (tage.isEmpty()) return@Canvas
            val balkenBreite = size.width / tage.size
            tage.forEachIndexed { index, tag ->
                val farbe = tag.ampel?.let { ampelFarbe(it, hoherKontrast) } ?: Color.LightGray
                val x = index * balkenBreite
                val hoehe = if (tag.ampel != null) size.height else size.height * LEERER_TAG_HOEHE_ANTEIL
                drawRect(
                    color = farbe,
                    topLeft = Offset(x, size.height - hoehe),
                    size = Size(balkenBreite * BALKEN_BREITE_ANTEIL, hoehe),
                )
            }
        }
        AmpelLegende(hoherKontrast = hoherKontrast, modifier = Modifier.padding(top = 8.dp))
    }
}

/** Datum wird hier bewusst nicht gebraucht (Anzeige ist reine Musteruebersicht). */
data class TagesAmpelUi(
    val ampel: Ampel?,
)

@Composable
fun AmpelLegende(
    hoherKontrast: Boolean = false,
    modifier: Modifier = Modifier,
) {
    val sprache = LocalSprache.current
    Row(modifier = modifier, horizontalArrangement = Arrangement.spacedBy(16.dp)) {
        Ampel.entries.forEach { ampel ->
            Row {
                Icon(ampel.icon(), contentDescription = null, tint = ampelFarbe(ampel, hoherKontrast))
                Text(" " + ampel.anzeigename(sprache), style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}
