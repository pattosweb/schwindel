package app.schwindeljournal.ui.steckbrief

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun SteckbriefScreen(
    onZurueck: () -> Unit = {},
    viewModel: SteckbriefViewModel = hiltViewModel(),
) {
    val draft = viewModel.draft
    val medikamente by viewModel.medikamente.collectAsStateWithLifecycle()
    val ansprechpartner by viewModel.ansprechpartner.collectAsStateWithLifecycle()

    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(24.dp),
    ) {
        TextButton(onClick = onZurueck, modifier = Modifier.heightIn(min = 48.dp)) {
            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null)
            Spacer(modifier = Modifier.width(6.dp))
            Text("Zurück zu Einstellungen")
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Mein Steckbrief", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text =
                "Einmal ausfüllen, hilft dir selbst beim Einordnen und ist das Erste, was du " +
                    "bei einem Arzttermin zusätzlich zu deinem Journal zeigen kannst. In jedem " +
                    "Modus vollständig sichtbar – wird nie gekürzt.",
            style = MaterialTheme.typography.bodyMedium,
        )
        Spacer(modifier = Modifier.height(24.dp))

        PersonAbschnitt(draft, viewModel::onDraftChange)
        AbschnittTrenner("Medikamente")
        MedikamenteAbschnitt(medikamente, viewModel::addMedikament, viewModel::deleteMedikament)
        AbschnittTrenner("Vorerkrankungen & Verletzungen")
        VorerkrankungenAbschnitt(draft, viewModel::onDraftChange)
        AbschnittTrenner("Verlauf")
        VorfallAbschnitt(draft, viewModel::onDraftChange)
        AbschnittTrenner("Blutdruck")
        BlutdruckAbschnitt(draft, viewModel::onDraftChange)
        AbschnittTrenner("Ansprechpartner")
        AnsprechpartnerAbschnitt(ansprechpartner, viewModel::addAnsprechpartner, viewModel::deleteAnsprechpartner)

        Spacer(modifier = Modifier.height(24.dp))
        Button(
            onClick = viewModel::speichern,
            modifier = Modifier.fillMaxWidth().heightIn(min = 48.dp),
        ) { Text("Speichern") }
        if (draft.gespeichert) {
            Spacer(modifier = Modifier.height(8.dp))
            Text("Gespeichert.", style = MaterialTheme.typography.bodySmall)
        }
    }
}

@Composable
private fun AbschnittTrenner(titel: String) {
    Spacer(modifier = Modifier.height(20.dp))
    HorizontalDivider()
    Spacer(modifier = Modifier.height(12.dp))
    Text(text = titel, style = MaterialTheme.typography.titleMedium)
    Spacer(modifier = Modifier.height(8.dp))
}
