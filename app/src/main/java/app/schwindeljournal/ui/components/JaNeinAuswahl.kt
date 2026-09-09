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

/** Einfache Ja/Nein-Auswahl fuer Steckbrief-Felder statt Checkbox ohne Beschriftung. */
@Composable
fun JaNeinAuswahl(
    ausgewaehlt: Boolean?,
    onAuswahl: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    val istEnglisch = LocalSprache.current == Sprache.EN
    Row(modifier = modifier, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        FilterChip(
            selected = ausgewaehlt == true,
            onClick = { onAuswahl(true) },
            label = { Text(if (istEnglisch) "Yes" else "Ja") },
            modifier = Modifier.heightIn(min = 48.dp),
        )
        FilterChip(
            selected = ausgewaehlt == false,
            onClick = { onAuswahl(false) },
            label = { Text(if (istEnglisch) "No" else "Nein") },
            modifier = Modifier.heightIn(min = 48.dp),
        )
    }
}
