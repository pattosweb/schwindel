package app.schwindeljournal.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import app.schwindeljournal.data.model.Sprache
import app.schwindeljournal.ui.shared.LocalSprache
import java.time.Year

private const val JAHRE_ZURUECK = 110
private const val LISTEN_HOEHE_DP = 360
private const val STANDARD_ALTER_JAHRE = 40

/**
 * Antippbare Jahr-Auswahl als scrollbare Liste statt Zifferntastatur (Bordmittel-
 * konsistent mit [DatumAuswahl]) - grosse Touch-Ziele statt praeziser Vier-Ziffern-
 * Eingabe (UX-Leitplanke, CLAUDE.md: eingeschraenkte Motorik/Sehkraft moeglich).
 */
@Composable
fun JahrAuswahl(
    label: String,
    ausgewaehltesJahr: Int?,
    onJahrGewaehlt: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    var dialogOffen by remember { mutableStateOf(false) }
    val istEnglisch = LocalSprache.current == Sprache.EN

    OutlinedButton(
        onClick = { dialogOffen = true },
        modifier = modifier.heightIn(min = 48.dp),
    ) {
        Text(
            when {
                ausgewaehltesJahr != null -> "$label: $ausgewaehltesJahr"
                istEnglisch -> "Select $label"
                else -> "$label auswählen"
            },
        )
    }

    if (dialogOffen) {
        JahrAuswahlDialog(
            label = label,
            ausgewaehltesJahr = ausgewaehltesJahr,
            onJahrGewaehlt = { jahr ->
                onJahrGewaehlt(jahr)
                dialogOffen = false
            },
            onAbbrechen = { dialogOffen = false },
        )
    }
}

@Composable
private fun JahrAuswahlDialog(
    label: String,
    ausgewaehltesJahr: Int?,
    onJahrGewaehlt: (Int) -> Unit,
    onAbbrechen: () -> Unit,
) {
    val aktuellesJahr = remember { Year.now().value }
    val jahre = remember(aktuellesJahr) { (aktuellesJahr downTo aktuellesJahr - JAHRE_ZURUECK).toList() }
    val listenZustand = rememberLazyListState()

    LaunchedEffect(Unit) {
        val zielIndex = jahre.indexOf(ausgewaehltesJahr ?: (aktuellesJahr - STANDARD_ALTER_JAHRE))
        if (zielIndex >= 0) listenZustand.scrollToItem(zielIndex)
    }

    val istEnglisch = LocalSprache.current == Sprache.EN
    AlertDialog(
        onDismissRequest = onAbbrechen,
        title = { Text(label) },
        text = {
            LazyColumn(state = listenZustand, modifier = Modifier.height(LISTEN_HOEHE_DP.dp)) {
                itemsIndexed(jahre, key = { _, jahr -> jahr }) { _, jahr ->
                    JahrZeile(jahr = jahr, ausgewaehlt = jahr == ausgewaehltesJahr, onClick = { onJahrGewaehlt(jahr) })
                }
            }
        },
        confirmButton = {},
        dismissButton = { TextButton(onClick = onAbbrechen) { Text(if (istEnglisch) "Cancel" else "Abbrechen") } },
    )
}

@Composable
private fun JahrZeile(
    jahr: Int,
    ausgewaehlt: Boolean,
    onClick: () -> Unit,
) {
    Surface(onClick = onClick, modifier = Modifier.fillMaxWidth().heightIn(min = 48.dp)) {
        val hintergrund =
            if (ausgewaehlt) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surface
        Box(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .background(hintergrund)
                    .padding(horizontal = 16.dp, vertical = 12.dp),
        ) {
            Text(
                text = "$jahr",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = if (ausgewaehlt) FontWeight.Bold else FontWeight.Normal,
            )
        }
    }
}
