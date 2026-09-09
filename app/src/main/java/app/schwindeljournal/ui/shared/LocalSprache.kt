package app.schwindeljournal.ui.shared

import androidx.compose.runtime.staticCompositionLocalOf
import app.schwindeljournal.data.model.Sprache

/**
 * Ambiente Sprache fuer die gesamte Compose-Oberflaeche (Phase 5, Mehrsprachigkeit) -
 * analog zu MaterialTheme/LocalContext: einmal in [app.schwindeljournal.ui.navigation.
 * SchwindeljournalNavGraph] bereitgestellt (zentrale Quelle, gleiches Prinzip wie
 * ModusViewModel.modus), statt als Parameter durch jeden Composable durchgereicht zu
 * werden. Default DE nur als Sicherheitsnetz, falls irgendwo ohne Provider genutzt.
 */
val LocalSprache = staticCompositionLocalOf { Sprache.DE }
