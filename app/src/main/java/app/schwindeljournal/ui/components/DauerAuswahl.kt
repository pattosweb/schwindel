package app.schwindeljournal.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.heightIn
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import app.schwindeljournal.data.model.Sprache
import app.schwindeljournal.ui.shared.LocalSprache

private const val UNTER_1_MIN_SEK = 30
private const val BIS_5_MIN_SEK = 180
private const val BIS_15_MIN_SEK = 600
private const val UEBER_15_MIN_SEK = 1_200

private fun dauerPresets(sprache: Sprache): List<Pair<String, Int>> =
    when (sprache) {
        Sprache.EN ->
            listOf(
                "< 1 min" to UNTER_1_MIN_SEK,
                "1–5 min" to BIS_5_MIN_SEK,
                "5–15 min" to BIS_15_MIN_SEK,
                "> 15 min" to UEBER_15_MIN_SEK,
            )
        else ->
            listOf(
                "< 1 Min" to UNTER_1_MIN_SEK,
                "1–5 Min" to BIS_5_MIN_SEK,
                "5–15 Min" to BIS_15_MIN_SEK,
                "> 15 Min" to UEBER_15_MIN_SEK,
            )
    }

/** Antippbare Dauer-Bereiche statt Freitext/Zahleneingabe – schnell, keine Tastatur noetig. */
@Composable
fun DauerAuswahl(
    ausgewaehlteSekunden: Int?,
    onDauerGewaehlt: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(modifier = modifier, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        dauerPresets(LocalSprache.current).forEach { (label, sekunden) ->
            FilterChip(
                selected = ausgewaehlteSekunden == sekunden,
                onClick = { onDauerGewaehlt(sekunden) },
                label = { Text(label) },
                modifier = Modifier.heightIn(min = 48.dp),
            )
        }
    }
}
