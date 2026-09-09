package app.schwindeljournal.ui.uebungsbegleiter

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
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
import kotlinx.coroutines.delay

/**
 * Übungs-Begleiter: Liste der Teil-E-Selbsthilfe-Übungen, je Übung ein einfacher
 * Countdown-Timer und ein Wiederholungszähler als Bedienhilfe. Keine Animationen
 * (UX-Leitplanke), alle Bedienelemente >=48dp Touch-Ziel. Kein Freitextfeld nötig,
 * daher entfällt hier die Spracheingabe-Alternative (nur Zahlen-Stepper).
 */
@Composable
fun UebungsBegleiterScreen(
    modus: Modus?,
    viewModel: UebungsBegleiterViewModel = hiltViewModel(),
) {
    val effektiverModus = modus ?: Modus.QUICK
    val sprache = LocalSprache.current
    LaunchedEffect(effektiverModus, sprache) { viewModel.onModusUndSpracheBekannt(effektiverModus, sprache) }
    val uebungen by viewModel.uebungen.collectAsStateWithLifecycle()

    var ausgewaehlteId by rememberSaveable { mutableStateOf<String?>(null) }
    val ausgewaehlteUebung = uebungen.firstOrNull { it.id == ausgewaehlteId }

    when {
        ausgewaehlteUebung != null ->
            UebungsDetail(
                block = ausgewaehlteUebung,
                onZurueck = { ausgewaehlteId = null },
            )
        effektiverModus == Modus.QUICK -> HinweisText(nurVollePrivilegierteModiText(sprache))
        uebungen.isEmpty() -> HinweisText(ladeUebungenText(sprache))
        else ->
            LazyColumn(modifier = Modifier.fillMaxSize().padding(16.dp)) {
                items(uebungen, key = { it.id }) { block ->
                    UebungsListenEintrag(block = block, onClick = { ausgewaehlteId = block.id })
                }
            }
    }
}

@Composable
private fun HinweisText(text: String) {
    Box(modifier = Modifier.fillMaxSize().padding(24.dp)) {
        Text(text = text, style = MaterialTheme.typography.bodyMedium)
    }
}

@Composable
private fun UebungsListenEintrag(
    block: ContentBlockEntity,
    onClick: () -> Unit,
) {
    OutlinedCard(
        onClick = onClick,
        modifier =
            Modifier
                .fillMaxWidth()
                .heightIn(min = 56.dp)
                .padding(vertical = 6.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(text = block.titel, style = MaterialTheme.typography.titleMedium)
            Icon(Icons.Filled.ChevronRight, contentDescription = null)
        }
    }
}

@Composable
private fun UebungsDetail(
    block: ContentBlockEntity,
    onZurueck: () -> Unit,
) {
    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .padding(16.dp),
    ) {
        val sprache = LocalSprache.current
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = onZurueck, modifier = Modifier.heightIn(min = 48.dp)) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = zurueckBeschreibung(sprache))
            }
            Spacer(modifier = Modifier.width(4.dp))
            Text(text = block.titel, style = MaterialTheme.typography.titleLarge)
        }
        Spacer(modifier = Modifier.height(12.dp))
        MarkdownText(markdown = block.inhaltMarkdown)
        Spacer(modifier = Modifier.height(20.dp))
        HorizontalDivider()
        Spacer(modifier = Modifier.height(12.dp))
        // key = block.id: Timer/Zähler starten pro Übung wieder bei den Vorgabewerten.
        TimerAbschnitt(key = block.id, sprache = sprache)
        Spacer(modifier = Modifier.height(20.dp))
        HorizontalDivider()
        Spacer(modifier = Modifier.height(12.dp))
        ZaehlerAbschnitt(key = block.id, sprache = sprache)
    }
}

private const val START_SEKUNDEN = 60
private const val SCHRITT_SEKUNDEN = 15
private const val MIN_SEKUNDEN = 15
private const val MAX_SEKUNDEN = 300
private const val TICK_MILLIS = 1_000L

@Composable
private fun TimerAbschnitt(
    key: String,
    sprache: Sprache,
) {
    var vorgabeSekunden by remember(key) { mutableIntStateOf(START_SEKUNDEN) }
    var verbleibendeSekunden by remember(key) { mutableIntStateOf(START_SEKUNDEN) }
    var laeuft by remember(key) { mutableStateOf(false) }

    LaunchedEffect(key, laeuft) {
        while (laeuft && verbleibendeSekunden > 0) {
            delay(TICK_MILLIS)
            verbleibendeSekunden -= 1
        }
        if (verbleibendeSekunden <= 0) laeuft = false
    }

    Column {
        Text(text = "Timer", style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = formatiereRestzeit(verbleibendeSekunden),
            style = MaterialTheme.typography.displaySmall,
        )
        if (verbleibendeSekunden <= 0) {
            Text(
                text = if (sprache == Sprache.EN) "Done." else "Fertig.",
                style = MaterialTheme.typography.bodyMedium,
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        TimerDauerSteuerung(
            laeuft = laeuft,
            onDauerAendern = { delta ->
                vorgabeSekunden = (vorgabeSekunden + delta).coerceIn(MIN_SEKUNDEN, MAX_SEKUNDEN)
                verbleibendeSekunden = vorgabeSekunden
            },
        )
        Spacer(modifier = Modifier.height(8.dp))
        TimerStartSteuerung(
            laeuft = laeuft,
            sprache = sprache,
            onStartPause = {
                if (verbleibendeSekunden <= 0) verbleibendeSekunden = vorgabeSekunden
                laeuft = !laeuft
            },
            onReset = {
                laeuft = false
                verbleibendeSekunden = vorgabeSekunden
            },
        )
    }
}

@Composable
private fun TimerDauerSteuerung(
    laeuft: Boolean,
    onDauerAendern: (Int) -> Unit,
) {
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        OutlinedButton(
            onClick = { if (!laeuft) onDauerAendern(-SCHRITT_SEKUNDEN) },
            modifier = Modifier.heightIn(min = 48.dp),
        ) { Text("-15s") }
        OutlinedButton(
            onClick = { if (!laeuft) onDauerAendern(SCHRITT_SEKUNDEN) },
            modifier = Modifier.heightIn(min = 48.dp),
        ) { Text("+15s") }
    }
}

@Composable
private fun TimerStartSteuerung(
    laeuft: Boolean,
    sprache: Sprache,
    onStartPause: () -> Unit,
    onReset: () -> Unit,
) {
    val zuruecksetzenText = if (sprache == Sprache.EN) "Reset" else "Zurücksetzen"
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        OutlinedButton(onClick = onStartPause, modifier = Modifier.heightIn(min = 48.dp)) {
            Icon(if (laeuft) Icons.Filled.Pause else Icons.Filled.PlayArrow, contentDescription = null)
            Spacer(modifier = Modifier.width(6.dp))
            Text(if (laeuft) "Pause" else "Start")
        }
        OutlinedButton(onClick = onReset, modifier = Modifier.heightIn(min = 48.dp)) {
            Icon(Icons.Filled.Refresh, contentDescription = null)
            Spacer(modifier = Modifier.width(6.dp))
            Text(zuruecksetzenText)
        }
    }
}

@Composable
private fun ZaehlerAbschnitt(
    key: String,
    sprache: Sprache,
) {
    var wiederholungen by remember(key) { mutableIntStateOf(0) }
    val istEnglisch = sprache == Sprache.EN

    Column {
        Text(
            text = if (istEnglisch) "Repetition counter" else "Wiederholungszähler",
            style = MaterialTheme.typography.titleMedium,
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "$wiederholungen", style = MaterialTheme.typography.displaySmall)
        Spacer(modifier = Modifier.height(8.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedButton(
                onClick = { wiederholungen += 1 },
                modifier = Modifier.heightIn(min = 48.dp),
            ) { Text(if (istEnglisch) "+1 repetition" else "+1 Wiederholung") }
            OutlinedButton(
                onClick = { wiederholungen = 0 },
                modifier = Modifier.heightIn(min = 48.dp),
            ) { Text(if (istEnglisch) "Reset" else "Zurücksetzen") }
        }
    }
}

private const val SEKUNDEN_PRO_MINUTE = 60

private fun formatiereRestzeit(sekunden: Int): String {
    val minuten = sekunden / SEKUNDEN_PRO_MINUTE
    val restSekunden = sekunden % SEKUNDEN_PRO_MINUTE
    return "%d:%02d".format(minuten, restSekunden)
}
