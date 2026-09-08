package app.schwindeljournal.ui.steckbrief

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import app.schwindeljournal.ui.components.DatumAuswahl
import app.schwindeljournal.ui.components.JaNeinAuswahl
import app.schwindeljournal.ui.components.SprachEingabeTextField

private typealias DraftUpdate = ((SteckbriefUiState) -> SteckbriefUiState) -> Unit

private const val GEBURTSJAHR_STELLEN = 4

@Composable
fun PersonAbschnitt(
    draft: SteckbriefUiState,
    onDraftChange: DraftUpdate,
) {
    Text("Zur Person", style = MaterialTheme.typography.titleMedium)
    Spacer(modifier = Modifier.height(8.dp))
    OutlinedTextField(
        value = draft.geburtsjahr,
        onValueChange = { text ->
            onDraftChange { it.copy(geburtsjahr = text.filter(Char::isDigit).take(GEBURTSJAHR_STELLEN)) }
        },
        label = { Text("Geburtsjahr") },
        keyboardOptions =
            androidx.compose.foundation.text
                .KeyboardOptions(keyboardType = KeyboardType.Number),
        modifier = Modifier.fillMaxWidth(),
    )
    Spacer(modifier = Modifier.height(12.dp))
    Text(
        "Beruf/Tätigkeit mit viel Bildschirmarbeit oder einseitiger Haltung?",
        style = MaterialTheme.typography.bodyMedium,
    )
    Spacer(modifier = Modifier.height(4.dp))
    JaNeinAuswahl(
        ausgewaehlt = draft.berufMitBelastung,
        onAuswahl = { wert -> onDraftChange { it.copy(berufMitBelastung = wert) } },
    )
}

@Composable
fun VorerkrankungenAbschnitt(
    draft: SteckbriefUiState,
    onDraftChange: DraftUpdate,
) {
    SprachEingabeTextField(
        value = draft.vorerkrankungen,
        onValueChange = { text -> onDraftChange { it.copy(vorerkrankungen = text) } },
        label = "Bekannte Vorerkrankungen/Diagnosen",
        minLines = 2,
    )
    Spacer(modifier = Modifier.height(12.dp))
    SprachEingabeTextField(
        value = draft.fruehereVerletzungen,
        onValueChange = { text -> onDraftChange { it.copy(fruehereVerletzungen = text) } },
        label = "Frühere Unfälle/Verletzungen Kopf-/Nackenbereich",
        minLines = 2,
    )
}

@Composable
fun VorfallAbschnitt(
    draft: SteckbriefUiState,
    onDraftChange: DraftUpdate,
) {
    Text("Ist das dein erster Schwindel-Vorfall?", style = MaterialTheme.typography.bodyMedium)
    Spacer(modifier = Modifier.height(4.dp))
    JaNeinAuswahl(
        ausgewaehlt = draft.ersterVorfall,
        onAuswahl = { wert -> onDraftChange { it.copy(ersterVorfall = wert) } },
    )
    if (draft.ersterVorfall == false) {
        Spacer(modifier = Modifier.height(12.dp))
        SprachEingabeTextField(
            value = draft.seitWannWiederkehrend,
            onValueChange = { text -> onDraftChange { it.copy(seitWannWiederkehrend = text) } },
            label = "Seit wann tritt Schwindel wiederkehrend auf?",
        )
    }
}

@Composable
fun BlutdruckAbschnitt(
    draft: SteckbriefUiState,
    onDraftChange: DraftUpdate,
) {
    SprachEingabeTextField(
        value = draft.letzterBlutdruck,
        onValueChange = { text -> onDraftChange { it.copy(letzterBlutdruck = text) } },
        label = "Letzter gemessener Blutdruck",
    )
    Spacer(modifier = Modifier.height(8.dp))
    DatumAuswahl(
        label = "Gemessen am",
        ausgewaehltesDatum = draft.letzterBlutdruckDatum,
        onDatumGewaehlt = { datum -> onDraftChange { it.copy(letzterBlutdruckDatum = datum) } },
    )
}
