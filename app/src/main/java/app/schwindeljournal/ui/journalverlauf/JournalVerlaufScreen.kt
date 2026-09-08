package app.schwindeljournal.ui.journalverlauf

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import app.schwindeljournal.data.local.entity.JournalEntryEntity
import app.schwindeljournal.data.model.Ampel
import app.schwindeljournal.data.model.Modus
import app.schwindeljournal.data.model.anzeigename
import app.schwindeljournal.data.model.icon
import app.schwindeljournal.ui.components.formatiereUhrzeit
import app.schwindeljournal.ui.theme.AmpelGelb
import app.schwindeljournal.ui.theme.AmpelGruen
import app.schwindeljournal.ui.theme.AmpelRot

@Composable
fun JournalVerlaufScreen(
    modus: Modus?,
    viewModel: JournalVerlaufViewModel = hiltViewModel(),
) {
    val eintraege by viewModel.eintraege.collectAsStateWithLifecycle()

    if (eintraege.isEmpty()) {
        Box(modifier = Modifier.fillMaxSize().padding(24.dp)) {
            Text(
                text = "Noch keine Einträge. Nutze \"Erfassen\", um deinen ersten Eintrag anzulegen.",
                style = MaterialTheme.typography.bodyMedium,
            )
        }
        return
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items(eintraege, key = { it.id }) { eintrag ->
            JournalEintragKarte(eintrag = eintrag, kompakt = modus == Modus.QUICK)
        }
    }
}

private fun ampelFarbe(ampel: Ampel) =
    when (ampel) {
        Ampel.GRUEN -> AmpelGruen
        Ampel.GELB -> AmpelGelb
        Ampel.ROT -> AmpelRot
    }

@Composable
private fun JournalEintragKarte(
    eintrag: JournalEntryEntity,
    kompakt: Boolean,
) {
    Card(modifier = Modifier.padding(vertical = 2.dp)) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = eintrag.ampel.icon(),
                    contentDescription = eintrag.ampel.anzeigename(),
                    tint = ampelFarbe(eintrag.ampel),
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "${eintrag.datum} · ${formatiereUhrzeit(eintrag.uhrzeit)}",
                    style = MaterialTheme.typography.titleSmall,
                )
            }
            if (!kompakt && !eintrag.situation.isNullOrBlank()) {
                Text(text = eintrag.situation, style = MaterialTheme.typography.bodyMedium)
            }
            if (!kompakt && !eintrag.warnzeichenKeinesAufgetreten) {
                Text(
                    text = "⚠ Warnzeichen aufgetreten",
                    style = MaterialTheme.typography.bodySmall,
                    color = AmpelRot,
                )
            }
        }
    }
}
