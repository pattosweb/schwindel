package app.schwindeljournal.ui.steckbrief

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import app.schwindeljournal.data.local.entity.AnsprechpartnerEntity
import app.schwindeljournal.data.local.entity.MedikamentEntity
import app.schwindeljournal.ui.components.DatumAuswahl
import app.schwindeljournal.ui.components.SprachEingabeTextField
import app.schwindeljournal.ui.components.formatiereDatum
import java.time.LocalDate

private val vordefinierteRollen = listOf("Hausarzt/-ärztin", "HNO", "Physio/Chiro")

@Composable
fun MedikamenteAbschnitt(
    medikamente: List<MedikamentEntity>,
    onHinzufuegen: (name: String, dosierung: String, seitWann: LocalDate?) -> Unit,
    onLoeschen: (MedikamentEntity) -> Unit,
) {
    Text(
        "Auch Präparate eintragen, die nebensächlich erscheinen – viele Medikamente " +
            "können Schwindel auslösen oder verstärken.",
        style = MaterialTheme.typography.bodySmall,
    )
    Spacer(modifier = Modifier.height(8.dp))
    medikamente.forEach { medikament -> MedikamentZeile(medikament, onLoeschen) }

    var name by remember { mutableStateOf("") }
    var dosierung by remember { mutableStateOf("") }
    var seitWann by remember { mutableStateOf<LocalDate?>(null) }

    SprachEingabeTextField(value = name, onValueChange = { name = it }, label = "Medikament")
    Spacer(modifier = Modifier.height(8.dp))
    SprachEingabeTextField(value = dosierung, onValueChange = { dosierung = it }, label = "Dosierung")
    Spacer(modifier = Modifier.height(8.dp))
    DatumAuswahl(label = "Seit wann", ausgewaehltesDatum = seitWann, onDatumGewaehlt = { seitWann = it })
    Spacer(modifier = Modifier.height(8.dp))
    OutlinedButton(
        onClick = {
            onHinzufuegen(name, dosierung, seitWann)
            name = ""
            dosierung = ""
            seitWann = null
        },
        enabled = name.isNotBlank(),
        modifier = Modifier.fillMaxWidth().heightIn(min = 48.dp),
    ) { Text("Medikament hinzufügen") }
}

@Composable
private fun MedikamentZeile(
    medikament: MedikamentEntity,
    onLoeschen: (MedikamentEntity) -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth().heightIn(min = 48.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(medikament.name, style = MaterialTheme.typography.bodyMedium)
            val details =
                listOfNotNull(
                    medikament.dosierung,
                    medikament.seitWann?.let { "seit ${formatiereDatum(it)}" },
                ).joinToString(" · ")
            if (details.isNotBlank()) {
                Text(details, style = MaterialTheme.typography.bodySmall)
            }
        }
        IconButton(onClick = { onLoeschen(medikament) }) {
            Icon(Icons.Filled.Delete, contentDescription = "${medikament.name} löschen")
        }
    }
}

@Composable
fun AnsprechpartnerAbschnitt(
    ansprechpartner: List<AnsprechpartnerEntity>,
    onHinzufuegen: (rolle: String, name: String, telefon: String) -> Unit,
    onLoeschen: (AnsprechpartnerEntity) -> Unit,
) {
    ansprechpartner.forEach { partner -> AnsprechpartnerZeile(partner, onLoeschen) }

    var rolle by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var telefon by remember { mutableStateOf("") }

    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        vordefinierteRollen.forEach { r ->
            OutlinedButton(onClick = { rolle = r }, modifier = Modifier.heightIn(min = 48.dp)) { Text(r) }
        }
    }
    Spacer(modifier = Modifier.height(8.dp))
    SprachEingabeTextField(value = rolle, onValueChange = { rolle = it }, label = "Rolle (z. B. Physio/Chiro)")
    Spacer(modifier = Modifier.height(8.dp))
    SprachEingabeTextField(value = name, onValueChange = { name = it }, label = "Name")
    Spacer(modifier = Modifier.height(8.dp))
    SprachEingabeTextField(value = telefon, onValueChange = { telefon = it }, label = "Telefon")
    Spacer(modifier = Modifier.height(8.dp))
    OutlinedButton(
        onClick = {
            onHinzufuegen(rolle, name, telefon)
            rolle = ""
            name = ""
            telefon = ""
        },
        enabled = rolle.isNotBlank(),
        modifier = Modifier.fillMaxWidth().heightIn(min = 48.dp),
    ) { Text("Ansprechpartner hinzufügen") }
}

@Composable
private fun AnsprechpartnerZeile(
    partner: AnsprechpartnerEntity,
    onLoeschen: (AnsprechpartnerEntity) -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth().heightIn(min = 48.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(partner.rolle, style = MaterialTheme.typography.bodyMedium)
            val details = listOfNotNull(partner.name, partner.telefon).joinToString(" · ")
            if (details.isNotBlank()) {
                Text(details, style = MaterialTheme.typography.bodySmall)
            }
        }
        IconButton(onClick = { onLoeschen(partner) }) {
            Icon(Icons.Filled.Delete, contentDescription = "${partner.rolle} löschen")
        }
    }
}
