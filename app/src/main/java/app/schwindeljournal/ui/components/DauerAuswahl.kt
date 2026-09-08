package app.schwindeljournal.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.heightIn
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

private val dauerPresets =
    listOf(
        "< 1 Min" to 30,
        "1–5 Min" to 180,
        "5–15 Min" to 600,
        "> 15 Min" to 1_200,
    )

/** Antippbare Dauer-Bereiche statt Freitext/Zahleneingabe – schnell, keine Tastatur noetig. */
@Composable
fun DauerAuswahl(
    ausgewaehlteSekunden: Int?,
    onDauerGewaehlt: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(modifier = modifier, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        dauerPresets.forEach { (label, sekunden) ->
            FilterChip(
                selected = ausgewaehlteSekunden == sekunden,
                onClick = { onDauerGewaehlt(sekunden) },
                label = { Text(label) },
                modifier = Modifier.heightIn(min = 48.dp),
            )
        }
    }
}
