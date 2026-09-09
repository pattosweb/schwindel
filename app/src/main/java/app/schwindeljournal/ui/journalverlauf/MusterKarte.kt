package app.schwindeljournal.ui.journalverlauf

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import app.schwindeljournal.data.model.Sprache

/**
 * Nur Kompass/Peer (datenmodell-und-content-mapping.md Abschnitt 2). Reine
 * Haeufigkeitsbeobachtungen aus strukturierten Feldern (Ampel, Begleitsymptome,
 * Trigger-Tags) - nie als Diagnose formuliert (UX-/Sicherheits-Leitplanke).
 */
@Composable
fun MusterKarte(
    hinweise: List<MusterHinweis>,
    sprache: Sprache,
    modifier: Modifier = Modifier,
) {
    val istEnglisch = sprache == Sprache.EN
    Card(modifier = modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                if (istEnglisch) "Possible patterns" else "Mögliche Muster",
                style = MaterialTheme.typography.titleMedium,
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                if (istEnglisch) {
                    "A purely descriptive frequency observation from your entries – not a diagnosis."
                } else {
                    "Reine Häufigkeitsbeobachtung aus deinen Einträgen – keine Diagnose."
                },
                style = MaterialTheme.typography.bodySmall,
            )
            Spacer(modifier = Modifier.height(8.dp))
            if (hinweise.isEmpty()) {
                Text(
                    if (istEnglisch) {
                        "Not enough entries yet for pattern hints."
                    } else {
                        "Noch nicht genug Einträge für Muster-Hinweise."
                    },
                    style = MaterialTheme.typography.bodyMedium,
                )
            } else {
                hinweise.forEach { hinweis ->
                    Text("• ${hinweis.render(sprache)}", style = MaterialTheme.typography.bodyMedium)
                }
            }
        }
    }
}
