package app.schwindeljournal.ui.einstellungen

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import app.schwindeljournal.data.local.entity.UserProfileEntity
import app.schwindeljournal.data.model.Modus
import app.schwindeljournal.ui.components.JaNeinAuswahl
import app.schwindeljournal.ui.components.ModusAuswahl
import app.schwindeljournal.ui.components.UhrzeitAuswahl
import java.time.LocalTime

private val STANDARD_ERINNERUNGSZEIT = LocalTime.of(20, 0)

@Composable
fun EinstellungenScreen(
    aktuellerModus: Modus?,
    onModusWechsel: (Modus) -> Unit,
    onSteckbriefOeffnen: () -> Unit,
    viewModel: EinstellungenViewModel = hiltViewModel(),
) {
    val profil by viewModel.profil.collectAsStateWithLifecycle()

    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(24.dp),
    ) {
        Text(text = "Einstellungen", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))
        ModusAbschnitt(aktuellerModus, onModusWechsel)
        AbschnittTrenner(
            "Steckbrief",
            "Persönliche Angaben, Medikamente und Ansprechpartner – in jedem Modus vollständig.",
        )
        OutlinedButton(
            onClick = onSteckbriefOeffnen,
            modifier = Modifier.fillMaxWidth().heightIn(min = 48.dp),
        ) { Text("Steckbrief öffnen") }
        AbschnittTrenner(
            "Erinnerung",
            "Erinnert dich einmal täglich ans Nachtragen – nur, wenn du heute noch nichts erfasst hast.",
        )
        ErinnerungAbschnitt(profil, viewModel)
        AbschnittTrenner(
            "Barrierefreiheit",
            "Passt die Ampel-Farben für Rot-Grün-Sehschwäche an. Symbol und Text bleiben in " +
                "jedem Fall zusätzlich zur Farbe sichtbar.",
        )
        BarrierefreiheitAbschnitt(profil, viewModel)
    }
}

@Composable
private fun BarrierefreiheitAbschnitt(
    profil: UserProfileEntity?,
    viewModel: EinstellungenViewModel,
) {
    Text(text = "Kontrastreiche Ampel-Farben", style = MaterialTheme.typography.titleSmall)
    Spacer(modifier = Modifier.height(8.dp))
    JaNeinAuswahl(
        ausgewaehlt = profil?.ampelHoherKontrast,
        onAuswahl = viewModel::onAmpelHoherKontrastChange,
    )
}

@Composable
private fun ModusAbschnitt(
    aktuellerModus: Modus?,
    onModusWechsel: (Modus) -> Unit,
) {
    Text(text = "Modus", style = MaterialTheme.typography.titleMedium)
    Spacer(modifier = Modifier.height(4.dp))
    Text(
        text = "Ein Wechsel ändert nur Anzeige und Formularumfang – keine bisherigen Daten gehen verloren.",
        style = MaterialTheme.typography.bodySmall,
    )
    Spacer(modifier = Modifier.height(12.dp))
    ModusAuswahl(ausgewaehlterModus = aktuellerModus, onModusGewaehlt = onModusWechsel)
}

@Composable
private fun AbschnittTrenner(
    titel: String,
    hinweis: String,
) {
    Spacer(modifier = Modifier.height(24.dp))
    HorizontalDivider()
    Spacer(modifier = Modifier.height(16.dp))
    Text(text = titel, style = MaterialTheme.typography.titleMedium)
    Spacer(modifier = Modifier.height(4.dp))
    Text(text = hinweis, style = MaterialTheme.typography.bodySmall)
    Spacer(modifier = Modifier.height(12.dp))
}

@Composable
private fun ErinnerungAbschnitt(
    profil: UserProfileEntity?,
    viewModel: EinstellungenViewModel,
) {
    val context = LocalContext.current
    val benachrichtigungsLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { gewaehrt ->
            if (gewaehrt) viewModel.onErinnerungAktivChange(true)
        }

    JaNeinAuswahl(
        ausgewaehlt = profil?.reminderAktiviert,
        onAuswahl = { aktiv ->
            val berechtigungNoetig =
                aktiv &&
                    Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
                    ContextCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) !=
                    PackageManager.PERMISSION_GRANTED
            if (berechtigungNoetig) {
                benachrichtigungsLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            } else {
                viewModel.onErinnerungAktivChange(aktiv)
            }
        },
    )
    if (profil?.reminderAktiviert == true) {
        Spacer(modifier = Modifier.height(12.dp))
        UhrzeitAuswahl(
            label = "Erinnerungszeit",
            ausgewaehlteUhrzeit = profil.reminderUhrzeit ?: STANDARD_ERINNERUNGSZEIT,
            onUhrzeitGewaehlt = viewModel::onErinnerungsUhrzeitChange,
        )
    }
}
