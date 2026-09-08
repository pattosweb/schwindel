package app.schwindeljournal.ui.wissensbibliothek

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.schwindeljournal.data.local.entity.ContentBlockEntity
import app.schwindeljournal.data.model.Modus
import app.schwindeljournal.data.repository.ContentBlockRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class WissensBibliothekViewModel
    @Inject
    constructor(
        repository: ContentBlockRepository,
    ) : ViewModel() {
        private val modus = MutableStateFlow<Modus?>(null)

        val bloecke: StateFlow<List<ContentBlockEntity>> =
            modus
                .filterNotNull()
                .flatMapLatest { repository.observeSichtbareBloecke(it) }
                .stateIn(viewModelScope, SharingStarted.WhileSubscribed(STOP_TIMEOUT_MILLIS), emptyList())

        fun onModusBekannt(aktuellerModus: Modus) {
            modus.value = aktuellerModus
        }

        private companion object {
            const val STOP_TIMEOUT_MILLIS = 5_000L
        }
    }
