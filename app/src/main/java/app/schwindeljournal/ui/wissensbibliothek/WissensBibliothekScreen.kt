package app.schwindeljournal.ui.wissensbibliothek

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import app.schwindeljournal.data.local.entity.ContentBlockEntity
import app.schwindeljournal.data.model.Modus
import app.schwindeljournal.data.model.Sprache
import app.schwindeljournal.ui.components.MarkdownText
import app.schwindeljournal.ui.shared.LocalSprache
import app.schwindeljournal.ui.theme.AmpelRot

@Composable
fun WissensBibliothekScreen(
    modus: Modus?,
    viewModel: WissensBibliothekViewModel = hiltViewModel(),
) {
    val effektiverModus = modus ?: Modus.QUICK
    val sprache = LocalSprache.current
    LaunchedEffect(effektiverModus, sprache) { viewModel.onModusUndSpracheBekannt(effektiverModus, sprache) }
    val bloecke by viewModel.bloecke.collectAsStateWithLifecycle()

    if (bloecke.isEmpty()) {
        Box(modifier = Modifier.fillMaxSize().padding(24.dp)) {
            Text(ladeText(sprache), style = MaterialTheme.typography.bodyMedium)
        }
        return
    }

    LazyColumn(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        items(bloecke, key = { it.id }) { block -> ContentBlockKarte(block) }
    }
}

@Composable
private fun ContentBlockKarte(block: ContentBlockEntity) {
    val istWarnzeichen = block.istWarnzeichenInhalt
    val sprache = LocalSprache.current
    Card(
        modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp),
        colors =
            if (istWarnzeichen) {
                CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.errorContainer)
            } else {
                CardDefaults.cardColors()
            },
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            if (istWarnzeichen) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Filled.Warning, contentDescription = sicherheitshinweisText(sprache), tint = AmpelRot)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(block.titel, style = MaterialTheme.typography.titleMedium)
                }
            } else {
                Text(block.titel, style = MaterialTheme.typography.titleMedium)
            }
            Spacer(modifier = Modifier.height(8.dp))
            MarkdownText(markdown = block.inhaltMarkdown)
        }
    }
}

private fun ladeText(sprache: Sprache): String =
    when (sprache) {
        Sprache.EN -> "Loading content …"
        else -> "Inhalte werden geladen …"
    }

private fun sicherheitshinweisText(sprache: Sprache): String =
    when (sprache) {
        Sprache.EN -> "Safety notice"
        else -> "Sicherheitshinweis"
    }
