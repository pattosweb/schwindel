package app.schwindeljournal.ui.steckbrief

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import app.schwindeljournal.data.model.Sprache
import app.schwindeljournal.ui.components.DatumAuswahl
import app.schwindeljournal.ui.components.JaNeinAuswahl
import app.schwindeljournal.ui.components.JahrAuswahl
import app.schwindeljournal.ui.components.SprachEingabeTextField
import app.schwindeljournal.ui.shared.LocalSprache

private typealias DraftUpdate = ((SteckbriefUiState) -> SteckbriefUiState) -> Unit

@Composable
fun PersonAbschnitt(
    draft: SteckbriefUiState,
    onDraftChange: DraftUpdate,
) {
    val istEnglisch = LocalSprache.current == Sprache.EN
    Text(if (istEnglisch) "About you" else "Zur Person", style = MaterialTheme.typography.titleMedium)
    Spacer(modifier = Modifier.height(8.dp))
    JahrAuswahl(
        label = if (istEnglisch) "Birth year" else "Geburtsjahr",
        ausgewaehltesJahr = draft.geburtsjahr,
        onJahrGewaehlt = { jahr -> onDraftChange { it.copy(geburtsjahr = jahr) } },
        modifier = Modifier.fillMaxWidth(),
    )
    Spacer(modifier = Modifier.height(12.dp))
    Text(
        if (istEnglisch) {
            "Job/activity with a lot of screen work or one-sided posture?"
        } else {
            "Beruf/Tätigkeit mit viel Bildschirmarbeit oder einseitiger Haltung?"
        },
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
    val istEnglisch = LocalSprache.current == Sprache.EN
    SprachEingabeTextField(
        value = draft.vorerkrankungen,
        onValueChange = { text -> onDraftChange { it.copy(vorerkrankungen = text) } },
        label = if (istEnglisch) "Known pre-existing conditions/diagnoses" else "Bekannte Vorerkrankungen/Diagnosen",
        minLines = 2,
    )
    Spacer(modifier = Modifier.height(12.dp))
    SprachEingabeTextField(
        value = draft.fruehereVerletzungen,
        onValueChange = { text -> onDraftChange { it.copy(fruehereVerletzungen = text) } },
        label =
            if (istEnglisch) {
                "Previous accidents/injuries to head/neck"
            } else {
                "Frühere Unfälle/Verletzungen Kopf-/Nackenbereich"
            },
        minLines = 2,
    )
}

@Composable
fun VorfallAbschnitt(
    draft: SteckbriefUiState,
    onDraftChange: DraftUpdate,
) {
    val istEnglisch = LocalSprache.current == Sprache.EN
    Text(
        if (istEnglisch) "Is this your first episode of vertigo?" else "Ist das dein erster Schwindel-Vorfall?",
        style = MaterialTheme.typography.bodyMedium,
    )
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
            label = if (istEnglisch) "Since when does it recur?" else "Seit wann tritt Schwindel wiederkehrend auf?",
        )
    }
}

@Composable
fun BlutdruckAbschnitt(
    draft: SteckbriefUiState,
    onDraftChange: DraftUpdate,
) {
    val istEnglisch = LocalSprache.current == Sprache.EN
    SprachEingabeTextField(
        value = draft.letzterBlutdruck,
        onValueChange = { text -> onDraftChange { it.copy(letzterBlutdruck = text) } },
        label = if (istEnglisch) "Last measured blood pressure" else "Letzter gemessener Blutdruck",
    )
    Spacer(modifier = Modifier.height(8.dp))
    DatumAuswahl(
        label = if (istEnglisch) "Measured on" else "Gemessen am",
        ausgewaehltesDatum = draft.letzterBlutdruckDatum,
        onDatumGewaehlt = { datum -> onDraftChange { it.copy(letzterBlutdruckDatum = datum) } },
    )
}
