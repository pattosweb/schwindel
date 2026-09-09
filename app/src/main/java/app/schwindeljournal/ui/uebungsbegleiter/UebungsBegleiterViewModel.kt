package app.schwindeljournal.ui.uebungsbegleiter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.schwindeljournal.data.local.entity.ContentBlockEntity
import app.schwindeljournal.data.model.BuchTeil
import app.schwindeljournal.data.model.Modus
import app.schwindeljournal.data.model.Sprache
import app.schwindeljournal.data.repository.ContentBlockRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

/**
 * Übungs-Begleiter zeigt nur Teil-E-Blöcke (Selbsthilfe-Übungen). Nutzt bewusst
 * die bestehende [ContentBlockRepository.observeSichtbareBloecke]-Abfrage statt einer
 * eigenen DAO-Query (Erst-Bordmittel-Prinzip, CLAUDE.md) und filtert clientseitig auf
 * Teil E. Im Quick-Modus sind laut Content-Mapping keine Teil-E-Blöcke sichtbar
 * (Quick zeigt nur Warnzeichen + Kurzglossar) - das ist beabsichtigt, siehe Screen.
 */
@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class UebungsBegleiterViewModel
    @Inject
    constructor(
        repository: ContentBlockRepository,
    ) : ViewModel() {
        private val modusUndSprache = MutableStateFlow<Pair<Modus, Sprache>?>(null)

        val uebungen: StateFlow<List<ContentBlockEntity>> =
            modusUndSprache
                .filterNotNull()
                .flatMapLatest { (aktuellerModus, aktuelleSprache) ->
                    repository.observeSichtbareBloecke(aktuellerModus, aktuelleSprache).map { bloecke ->
                        bloecke
                            .filter { it.buchTeil == BuchTeil.E }
                            .sortedBy { it.titel }
                    }
                }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(STOP_TIMEOUT_MILLIS), emptyList())

        fun onModusUndSpracheBekannt(
            aktuellerModus: Modus,
            aktuelleSprache: Sprache,
        ) {
            modusUndSprache.value = aktuellerModus to aktuelleSprache
        }

        private companion object {
            const val STOP_TIMEOUT_MILLIS = 5_000L
        }
    }
