package app.schwindeljournal.ui.journalverlauf

import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import app.schwindeljournal.data.local.entity.JournalEntryEntity
import app.schwindeljournal.data.model.Modus
import app.schwindeljournal.data.model.anzeigename
import app.schwindeljournal.data.model.icon
import app.schwindeljournal.data.model.journalFensterTage
import app.schwindeljournal.ui.components.TagesAmpelUi
import app.schwindeljournal.ui.components.ZeitverlaufsDiagramm
import app.schwindeljournal.ui.components.ampelFarbe
import app.schwindeljournal.ui.components.formatiereUhrzeit
import app.schwindeljournal.ui.theme.AmpelRot

@Composable
fun JournalVerlaufScreen(
    modus: Modus?,
    viewModel: JournalVerlaufViewModel = hiltViewModel(),
) {
    val eintraege by viewModel.eintraege.collectAsStateWithLifecycle()
    val symptome by viewModel.symptome.collectAsStateWithLifecycle()
    val profil by viewModel.profil.collectAsStateWithLifecycle()
    val pdfStatus = viewModel.pdfExportStatus
    val effektiverModus = modus ?: Modus.QUICK

    PdfTeilenEffekt(pdfStatus = pdfStatus, onErledigt = viewModel::pdfExportStatusZurueckgesetzt)

    if (eintraege.isEmpty()) {
        Box(modifier = Modifier.fillMaxSize().padding(24.dp)) {
            Text(
                text = "Noch keine Einträge. Nutze \"Erfassen\", um deinen ersten Eintrag anzulegen.",
                style = MaterialTheme.typography.bodyMedium,
            )
        }
        return
    }

    val fensterTage = journalFensterTage(effektiverModus, profil?.journalFensterErweitert)
    val tagesAmpel = remember(eintraege, fensterTage) { berechneTagesAmpel(eintraege, fensterTage) }
    val musterHinweise =
        remember(eintraege, symptome, effektiverModus) {
            if (effektiverModus == Modus.QUICK) {
                emptyList()
            } else {
                berechneSymptomMuster(eintraege, symptome) + berechneTriggerMuster(eintraege)
            }
        }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        item {
            AuswertungsKopf(
                zustand =
                    AuswertungsKopfZustand(
                        modus = effektiverModus,
                        tagesAmpel = tagesAmpel,
                        fensterTage = fensterTage,
                        journalFensterErweitert = profil?.journalFensterErweitert == true,
                        musterHinweise = musterHinweise,
                        pdfExportLaeuft = pdfStatus is PdfExportStatus.Laeuft,
                    ),
                onJournalFensterErweitertToggle = viewModel::onJournalFensterErweitertToggle,
                onPdfExport = viewModel::exportierePdf,
            )
        }
        items(eintraege, key = { it.id }) { eintrag ->
            JournalEintragKarte(
                eintrag = eintrag,
                kompakt = effektiverModus == Modus.QUICK,
                zeigeReflexionsVorschau = effektiverModus == Modus.PEER,
            )
        }
    }
}

private data class AuswertungsKopfZustand(
    val modus: Modus,
    val tagesAmpel: List<TagesAmpel>,
    val fensterTage: Int,
    val journalFensterErweitert: Boolean,
    val musterHinweise: List<MusterHinweis>,
    val pdfExportLaeuft: Boolean,
)

@Composable
private fun PdfTeilenEffekt(
    pdfStatus: PdfExportStatus,
    onErledigt: () -> Unit,
) {
    val context = LocalContext.current
    LaunchedEffect(pdfStatus) {
        val fertig = pdfStatus as? PdfExportStatus.Fertig ?: return@LaunchedEffect
        val intent =
            Intent(Intent.ACTION_SEND).apply {
                type = "application/pdf"
                putExtra(Intent.EXTRA_STREAM, fertig.uri)
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            }
        context.startActivity(Intent.createChooser(intent, "Schwindeljournal-PDF teilen"))
        onErledigt()
    }
}

@Composable
private fun AuswertungsKopf(
    zustand: AuswertungsKopfZustand,
    onJournalFensterErweitertToggle: (Boolean) -> Unit,
    onPdfExport: () -> Unit,
) {
    Column {
        Text("Zeitverlauf (${zustand.fensterTage} Tage)", style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(8.dp))
        ZeitverlaufsDiagramm(tage = zustand.tagesAmpel.map { TagesAmpelUi(it.schlimmste) })

        if (zustand.modus == Modus.QUICK) {
            Spacer(modifier = Modifier.height(8.dp))
            FilterChip(
                selected = zustand.journalFensterErweitert,
                onClick = { onJournalFensterErweitertToggle(!zustand.journalFensterErweitert) },
                label = { Text("Journal-Fenster auf 60 Tage erweitern") },
                modifier = Modifier.heightIn(min = 48.dp),
            )
        }

        if (zustand.modus != Modus.QUICK) {
            Spacer(modifier = Modifier.height(16.dp))
            MusterKarte(hinweise = zustand.musterHinweise)
        }

        Spacer(modifier = Modifier.height(16.dp))
        OutlinedButton(
            onClick = onPdfExport,
            enabled = !zustand.pdfExportLaeuft,
            modifier = Modifier.fillMaxWidth().heightIn(min = 48.dp),
        ) {
            Text(if (zustand.pdfExportLaeuft) "PDF wird erstellt …" else "Als PDF fürs Arztgespräch exportieren")
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
private fun JournalEintragKarte(
    eintrag: JournalEntryEntity,
    kompakt: Boolean,
    zeigeReflexionsVorschau: Boolean,
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
            // Datenmodell-Doc Abschnitt 2: Peer zeigt in der Verlaufsansicht zusaetzlich
            // eine Reflexionstext-Vorschau (nur dieser Modus befuellt das Feld ueberhaupt).
            if (zeigeReflexionsVorschau && !eintrag.reflexionsfrageAntwort.isNullOrBlank()) {
                Text(
                    text = "\"${eintrag.reflexionsfrageAntwort}\"",
                    style = MaterialTheme.typography.bodySmall,
                    fontStyle = FontStyle.Italic,
                )
            }
        }
    }
}
