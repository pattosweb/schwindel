package app.schwindeljournal.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.heightIn
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/** Einfache Ja/Nein-Auswahl fuer Steckbrief-Felder statt Checkbox ohne Beschriftung. */
@Composable
fun JaNeinAuswahl(
    ausgewaehlt: Boolean?,
    onAuswahl: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(modifier = modifier, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        FilterChip(
            selected = ausgewaehlt == true,
            onClick = { onAuswahl(true) },
            label = { Text("Ja") },
            modifier = Modifier.heightIn(min = 48.dp),
        )
        FilterChip(
            selected = ausgewaehlt == false,
            onClick = { onAuswahl(false) },
            label = { Text("Nein") },
            modifier = Modifier.heightIn(min = 48.dp),
        )
    }
}
