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

/**
 * Nur Kompass/Peer (datenmodell-und-content-mapping.md Abschnitt 2). Reine
 * Haeufigkeitsbeobachtungen aus strukturierten Feldern (Ampel, Begleitsymptome,
 * Trigger-Tags) - nie als Diagnose formuliert (UX-/Sicherheits-Leitplanke).
 */
@Composable
fun MusterKarte(
    hinweise: List<MusterHinweis>,
    modifier: Modifier = Modifier,
) {
    Card(modifier = modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Mögliche Muster", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                "Reine Häufigkeitsbeobachtung aus deinen Einträgen – keine Diagnose.",
                style = MaterialTheme.typography.bodySmall,
            )
            Spacer(modifier = Modifier.height(8.dp))
            if (hinweise.isEmpty()) {
                Text(
                    "Noch nicht genug Einträge für Muster-Hinweise.",
                    style = MaterialTheme.typography.bodyMedium,
                )
            } else {
                hinweise.forEach { hinweis ->
                    Text("• ${hinweis.text}", style = MaterialTheme.typography.bodyMedium)
                }
            }
        }
    }
}
