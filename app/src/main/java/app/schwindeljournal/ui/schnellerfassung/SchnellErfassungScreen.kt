package app.schwindeljournal.ui.schnellerfassung

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import app.schwindeljournal.data.model.Modus
import app.schwindeljournal.data.model.Sprache
import app.schwindeljournal.ui.components.AmpelAuswahl
import app.schwindeljournal.ui.components.BegleitsymptomeAuswahl
import app.schwindeljournal.ui.components.DauerAuswahl
import app.schwindeljournal.ui.components.SprachEingabeTextField
import app.schwindeljournal.ui.components.TriggerTagAuswahl
import app.schwindeljournal.ui.components.formatiereUhrzeit
import app.schwindeljournal.ui.shared.LocalSprache

@Composable
fun SchnellErfassungScreen(
    modus: Modus?,
    ampelHoherKontrast: Boolean = false,
    viewModel: SchnellErfassungViewModel = hiltViewModel(),
) {
    val state = viewModel.uiState
    val effektiverModus = modus ?: Modus.QUICK
    val sprache = LocalSprache.current
    val istEnglisch = sprache == Sprache.EN

    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(24.dp),
    ) {
        Text(
            text = if (istEnglisch) "Quick Log" else "Schnell-Erfassung",
            style = MaterialTheme.typography.headlineMedium,
        )
        Spacer(modifier = Modifier.height(16.dp))

        AmpelAuswahl(
            ausgewaehlt = state.ampel,
            onAmpelGewaehlt = viewModel::onAmpelGewaehlt,
            hoherKontrast = ampelHoherKontrast,
        )
        Spacer(modifier = Modifier.height(16.dp))

        if (effektiverModus == Modus.QUICK) {
            SprachEingabeTextField(
                value = state.situation,
                onValueChange = viewModel::onSituationChange,
                label = if (istEnglisch) "Quick note" else "Kurznotiz",
            )
        } else {
            AusfuehrlicheFelder(state = state, modus = effektiverModus, viewModel = viewModel, sprache = sprache)
        }

        Spacer(modifier = Modifier.height(16.dp))
        WarnzeichenCheckbox(
            keinesAufgetreten = state.warnzeichenKeinesAufgetreten,
            onChange = viewModel::onWarnzeichenCheckboxChange,
            istEnglisch = istEnglisch,
        )

        Spacer(modifier = Modifier.height(24.dp))
        SpeichernAbschnitt(
            state = state,
            effektiverModus = effektiverModus,
            viewModel = viewModel,
            istEnglisch = istEnglisch,
        )
    }
}

@Composable
private fun SpeichernAbschnitt(
    state: SchnellErfassungUiState,
    effektiverModus: Modus,
    viewModel: SchnellErfassungViewModel,
    istEnglisch: Boolean,
) {
    Button(
        onClick = { viewModel.speichern(effektiverModus) },
        enabled = state.kannGespeichertWerden,
        modifier =
            Modifier
                .fillMaxWidth()
                .heightIn(min = 48.dp),
    ) {
        Text(if (istEnglisch) "Save" else "Speichern")
    }

    state.zuletztGespeichertUm?.let { uhrzeit ->
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text =
                if (istEnglisch) {
                    "Last saved at ${formatiereUhrzeit(uhrzeit)}."
                } else {
                    "Zuletzt gespeichert um ${formatiereUhrzeit(uhrzeit)} Uhr."
                },
            style = MaterialTheme.typography.bodySmall,
        )
    }
}

@Composable
private fun AusfuehrlicheFelder(
    state: SchnellErfassungUiState,
    modus: Modus,
    viewModel: SchnellErfassungViewModel,
    sprache: Sprache,
) {
    val istEnglisch = sprache == Sprache.EN
    if (modus == Modus.PEER) {
        SprachEingabeTextField(
            value = state.reflexionsfrage,
            onValueChange = viewModel::onReflexionsfrageChange,
            label = remember(sprache) { reflexionsfrageDesTages(sprache = sprache) },
            minLines = 2,
        )
        Spacer(modifier = Modifier.height(16.dp))
    }

    TextButton(onClick = viewModel::onDetailsToggle, modifier = Modifier.heightIn(min = 48.dp)) {
        Icon(
            imageVector = if (state.detailsAufgeklappt) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
            contentDescription = null,
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            when {
                state.detailsAufgeklappt && istEnglisch -> "Hide details"
                state.detailsAufgeklappt -> "Details ausblenden"
                istEnglisch -> "Add details"
                else -> "Details ergänzen"
            },
        )
    }

    if (state.detailsAufgeklappt) {
        DetailsInhalt(state = state, viewModel = viewModel, istEnglisch = istEnglisch)
    }

    Spacer(modifier = Modifier.height(16.dp))
    SprachEingabeTextField(
        value = state.tagesbewertung,
        onValueChange = viewModel::onTagesbewertungChange,
        label = if (istEnglisch) "Your own rating of the day" else "Eigene Tagesbewertung",
        minLines = 2,
    )
}

@Composable
private fun DetailsInhalt(
    state: SchnellErfassungUiState,
    viewModel: SchnellErfassungViewModel,
    istEnglisch: Boolean,
) {
    Spacer(modifier = Modifier.height(8.dp))
    SprachEingabeTextField(
        value = state.situation,
        onValueChange = viewModel::onSituationChange,
        label = if (istEnglisch) "Situation/activity" else "Situation/Tätigkeit",
    )
    Spacer(modifier = Modifier.height(8.dp))
    TriggerTagAuswahl(onTagAusgewaehlt = viewModel::onTriggerTagAusgewaehlt)

    Spacer(modifier = Modifier.height(16.dp))
    Text(
        if (istEnglisch) "Head/neck position beforehand" else "Kopf-/Nackenposition davor",
        style = MaterialTheme.typography.titleSmall,
    )
    Spacer(modifier = Modifier.height(4.dp))
    SprachEingabeTextField(
        value = state.kopfNackenPosition,
        onValueChange = viewModel::onKopfNackenPositionChange,
        label = if (istEnglisch) "e.g. \"looked up\"" else "z. B. \"nach oben geschaut\"",
    )

    Spacer(modifier = Modifier.height(16.dp))
    Text(if (istEnglisch) "Duration" else "Dauer", style = MaterialTheme.typography.titleSmall)
    Spacer(modifier = Modifier.height(4.dp))
    DauerAuswahl(ausgewaehlteSekunden = state.dauerSekunden, onDauerGewaehlt = viewModel::onDauerGewaehlt)

    Spacer(modifier = Modifier.height(16.dp))
    Text(
        if (istEnglisch) "Accompanying symptoms" else "Begleitsymptome",
        style = MaterialTheme.typography.titleSmall,
    )
    Spacer(modifier = Modifier.height(4.dp))
    BegleitsymptomeAuswahl(
        ausgewaehlt = state.symptome,
        sonstigesFreitext = state.sonstigesSymptomFreitext,
        onToggle = viewModel::onSymptomToggle,
        onSonstigesFreitextChange = viewModel::onSonstigesSymptomFreitextChange,
    )

    Spacer(modifier = Modifier.height(16.dp))
    SprachEingabeTextField(
        value = state.schlafqualitaet,
        onValueChange = viewModel::onSchlafqualitaetChange,
        label = if (istEnglisch) "Night before: sleep quality" else "Nacht davor: Schlafqualität",
    )
    Spacer(modifier = Modifier.height(8.dp))
    SprachEingabeTextField(
        value = state.kissenhoehe,
        onValueChange = viewModel::onKissenhoeheChange,
        label = if (istEnglisch) "Pillow height/type" else "Kissenhöhe/-art",
    )
}

@Composable
private fun WarnzeichenCheckbox(
    keinesAufgetreten: Boolean,
    onChange: (Boolean) -> Unit,
    istEnglisch: Boolean,
) {
    Row(
        modifier =
            Modifier
                .fillMaxWidth()
                .heightIn(min = 48.dp)
                .clickable { onChange(!keinesAufgetreten) },
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Checkbox(checked = keinesAufgetreten, onCheckedChange = onChange)
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            if (istEnglisch) "None of the warning signs occurred" else "Keines der Warnzeichen ist aufgetreten",
            style = MaterialTheme.typography.bodyMedium,
        )
    }
}
