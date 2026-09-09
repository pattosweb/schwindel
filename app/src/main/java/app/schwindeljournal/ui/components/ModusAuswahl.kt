package app.schwindeljournal.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import app.schwindeljournal.data.model.Modus
import app.schwindeljournal.data.model.Sprache
import app.schwindeljournal.data.model.anzeigename
import app.schwindeljournal.data.model.beschreibung
import app.schwindeljournal.data.model.buchtitel
import app.schwindeljournal.ui.shared.LocalSprache

private data class ModusInfo(
    val modus: Modus,
)

private val modusReihenfolge =
    listOf(
        ModusInfo(Modus.KOMPASS),
        ModusInfo(Modus.PEER),
        ModusInfo(Modus.QUICK),
    )

/**
 * Wiederverwendbare Modus-Auswahl (Engineering-Standard "Wiederverwendbarkeit") –
 * eingesetzt sowohl im Onboarding (Erstwahl) als auch in den Einstellungen
 * (jederzeitiger, verlustfreier Wechsel).
 */
@Composable
fun ModusAuswahl(
    ausgewaehlterModus: Modus?,
    onModusGewaehlt: (Modus) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(12.dp)) {
        modusReihenfolge.forEach { info ->
            ModusKarte(
                modus = info.modus,
                ausgewaehlt = ausgewaehlterModus == info.modus,
                onClick = { onModusGewaehlt(info.modus) },
            )
        }
    }
}

@Composable
private fun ModusKarte(
    modus: Modus,
    ausgewaehlt: Boolean,
    onClick: () -> Unit,
) {
    val sprache = LocalSprache.current
    val randfarbe = if (ausgewaehlt) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline
    OutlinedCard(
        onClick = onClick,
        modifier =
            Modifier
                .fillMaxWidth()
                .heightIn(min = 48.dp),
        border = BorderStroke(if (ausgewaehlt) 2.dp else 1.dp, randfarbe),
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                if (ausgewaehlt) {
                    Icon(
                        imageVector = Icons.Filled.CheckCircle,
                        contentDescription = if (sprache == Sprache.EN) "Selected" else "Ausgewählt",
                        tint = MaterialTheme.colorScheme.primary,
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                }
                Text(text = modus.anzeigename(sprache), style = MaterialTheme.typography.titleMedium)
            }
            Text(text = modus.buchtitel(sprache), style = MaterialTheme.typography.labelMedium)
            Spacer(modifier = Modifier.padding(top = 4.dp))
            Text(text = modus.beschreibung(sprache), style = MaterialTheme.typography.bodyMedium)
        }
    }
}
