package app.schwindeljournal.ui.wissensbibliothek

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

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class WissensBibliothekViewModel
    @Inject
    constructor(
        repository: ContentBlockRepository,
    ) : ViewModel() {
        private val modusUndSprache = MutableStateFlow<Pair<Modus, Sprache>?>(null)

        val bloecke: StateFlow<List<ContentBlockEntity>> =
            modusUndSprache
                .filterNotNull()
                .flatMapLatest { (aktuellerModus, aktuelleSprache) ->
                    repository.observeSichtbareBloecke(aktuellerModus, aktuelleSprache).map {
                        it.sortiertFuerAnzeige(aktuellerModus)
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

/**
 * Datenmodell-Doc Abschnitt 2: Peer zeigt "Teil G prominent oben". Warnzeichen bleibt
 * in jedem Modus ganz oben (Sicherheitsrelevanz), sonst normale Buchteil-Reihenfolge.
 */
private fun List<ContentBlockEntity>.sortiertFuerAnzeige(modus: Modus): List<ContentBlockEntity> =
    sortedWith(
        compareByDescending<ContentBlockEntity> { it.istWarnzeichenInhalt }
            .thenByDescending { modus == Modus.PEER && it.buchTeil == BuchTeil.G }
            .thenBy { it.buchTeil }
            .thenBy { it.titel },
    )
