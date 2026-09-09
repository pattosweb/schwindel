package app.schwindeljournal.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.heightIn
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import app.schwindeljournal.data.model.Sprache
import app.schwindeljournal.ui.shared.LocalSprache

/**
 * Vordefinierte, antippbare Situationstags (Roadmap Phase 1: "Trigger-Tag-Bibliothek").
 * Ein Tap fuegt den Tag der Situations-Freitextzeile hinzu – kein eigenes Datenfeld,
 * um das Superset-Schema (datenmodell-und-content-mapping.md) nicht aufzublaehen.
 * Sprachabhaengig, da der Tag direkt in den Freitext geschrieben wird (Auswertung.kt
 * matcht spaeter denselben sprachabhaengigen Tag-Text dagegen).
 */
fun vordefinierteTriggerTags(sprache: Sprache): List<String> =
    when (sprache) {
        Sprache.EN ->
            listOf(
                "Getting up",
                "Turned head",
                "Looked up",
                "Bent over",
                "Turned over in bed",
                "Screen work",
                "Walking/stairs",
                "Exercise",
                "Stress",
                "Little sleep",
            )
        else ->
            listOf(
                "Aufstehen",
                "Kopf gedreht",
                "Nach oben geschaut",
                "Bücken",
                "Im Bett umgedreht",
                "Bildschirmarbeit",
                "Gehen/Treppen",
                "Sport",
                "Stress",
                "Wenig Schlaf",
            )
    }

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun TriggerTagAuswahl(
    onTagAusgewaehlt: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    FlowRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        vordefinierteTriggerTags(LocalSprache.current).forEach { tag ->
            AssistChip(
                onClick = { onTagAusgewaehlt(tag) },
                label = { Text(tag) },
                modifier = Modifier.heightIn(min = 48.dp),
            )
        }
    }
}
