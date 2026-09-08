package app.schwindeljournal.ui.einstellungen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import app.schwindeljournal.data.model.Modus
import app.schwindeljournal.ui.components.ModusAuswahl

@Composable
fun EinstellungenScreen(
    aktuellerModus: Modus?,
    onModusWechsel: (Modus) -> Unit,
) {
    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(24.dp),
    ) {
        Text(text = "Einstellungen", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Modus", style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text =
                "Ein Wechsel ändert nur Anzeige und Formularumfang – keine bisherigen " +
                    "Daten gehen verloren.",
            style = MaterialTheme.typography.bodySmall,
        )
        Spacer(modifier = Modifier.height(12.dp))
        ModusAuswahl(
            ausgewaehlterModus = aktuellerModus,
            onModusGewaehlt = onModusWechsel,
        )
    }
}
