package app.schwindeljournal.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import app.schwindeljournal.data.model.Modus
import app.schwindeljournal.data.model.anzeigename

/**
 * Platzhalter fuer Screens, die erst in einer spaeteren Roadmap-Phase mit
 * echtem Inhalt gefuellt werden. Zeigt den zentral bereitgestellten Modus an,
 * um die zentrale Modus-Abfrage frueh zu verifizieren (Phase 0.5).
 */
@Composable
fun PlatzhalterScreen(
    titel: String,
    modus: Modus?,
) {
    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(text = titel, style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Aktueller Modus: ${modus?.anzeigename() ?: "…"}",
            style = MaterialTheme.typography.bodyMedium,
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Diese Ansicht wird in einer späteren Phase mit Inhalt gefüllt.",
            style = MaterialTheme.typography.bodySmall,
        )
    }
}
