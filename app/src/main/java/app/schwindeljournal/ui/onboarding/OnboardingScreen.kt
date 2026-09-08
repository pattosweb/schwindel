package app.schwindeljournal.ui.onboarding

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import app.schwindeljournal.data.model.Modus
import app.schwindeljournal.ui.components.ModusAuswahl

@Composable
fun OnboardingScreen(onModusGewaehlt: (Modus) -> Unit) {
    var ausgewaehlterModus by rememberSaveable { mutableStateOf<Modus?>(null) }

    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(24.dp),
    ) {
        Text(text = "Willkommen im Schwindeljournal", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text =
                "Wähle die Variante, die zu dir passt. Du kannst sie jederzeit in den " +
                    "Einstellungen wechseln, ohne etwas zu verlieren.",
            style = MaterialTheme.typography.bodyMedium,
        )
        Spacer(modifier = Modifier.height(24.dp))
        ModusAuswahl(
            ausgewaehlterModus = ausgewaehlterModus,
            onModusGewaehlt = { ausgewaehlterModus = it },
        )
        Spacer(modifier = Modifier.height(24.dp))
        Button(
            onClick = { ausgewaehlterModus?.let(onModusGewaehlt) },
            enabled = ausgewaehlterModus != null,
            modifier =
                Modifier
                    .fillMaxWidth()
                    .heightIn(min = 48.dp),
        ) {
            Text("Los geht's")
        }
    }
}
