package app.schwindeljournal.ui.journalverlauf

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.schwindeljournal.data.local.entity.JournalEntryEntity
import app.schwindeljournal.data.repository.JournalEntryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class JournalVerlaufViewModel
    @Inject
    constructor(
        repository: JournalEntryRepository,
    ) : ViewModel() {
        val eintraege: StateFlow<List<JournalEntryEntity>> =
            repository.alleEintraege
                .stateIn(viewModelScope, SharingStarted.WhileSubscribed(STOP_TIMEOUT_MILLIS), emptyList())

        private companion object {
            const val STOP_TIMEOUT_MILLIS = 5_000L
        }
    }
