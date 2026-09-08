package app.schwindeljournal.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import app.schwindeljournal.data.model.Ampel
import app.schwindeljournal.data.model.anzeigename
import app.schwindeljournal.data.model.beschreibung
import app.schwindeljournal.data.model.icon
import app.schwindeljournal.ui.theme.AmpelGelb
import app.schwindeljournal.ui.theme.AmpelGruen
import app.schwindeljournal.ui.theme.AmpelRot

/**
 * Schwindelbarometer-Auswahl. Jede Farbe zusaetzlich mit Symbol + Text (UX-Leitplanke,
 * nicht nur Farbcodierung), grosse Touch-Ziele (>=48dp) fuer eingeschraenkte Motorik.
 */
@Composable
fun AmpelAuswahl(
    ausgewaehlt: Ampel?,
    onAmpelGewaehlt: (Ampel) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Ampel.entries.forEach { ampel ->
            AmpelKarte(
                ampel = ampel,
                ausgewaehlt = ausgewaehlt == ampel,
                onClick = { onAmpelGewaehlt(ampel) },
            )
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
private fun AmpelKarte(
    ampel: Ampel,
    ausgewaehlt: Boolean,
    onClick: () -> Unit,
) {
    val farbe = ampelFarbe(ampel)
    OutlinedCard(
        onClick = onClick,
        modifier =
            Modifier
                .fillMaxWidth()
                .heightIn(min = 56.dp),
        border =
            BorderStroke(
                if (ausgewaehlt) 3.dp else 1.dp,
                if (ausgewaehlt) farbe else MaterialTheme.colorScheme.outline,
            ),
    ) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = ampel.icon(),
                contentDescription = null,
                tint = farbe,
                modifier = Modifier.size(32.dp),
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(
                    text = ampel.anzeigename(),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                )
                Text(text = ampel.beschreibung(), style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}
