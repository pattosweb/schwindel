package app.schwindeljournal.ui.steckbrief

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.schwindeljournal.data.local.entity.AnsprechpartnerEntity
import app.schwindeljournal.data.local.entity.MedikamentEntity
import app.schwindeljournal.data.local.entity.UserProfileEntity
import app.schwindeljournal.data.repository.UserProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class SteckbriefViewModel
    @Inject
    constructor(
        private val repository: UserProfileRepository,
    ) : ViewModel() {
        // Volles, zuletzt bekanntes Profil (fuer id/modus beim Speichern) - getrennt vom
        // UI-Draft, damit ein externes Update (z. B. Moduswechsel in den Einstellungen)
        // waehrend des Bearbeitens nicht laufende Eingaben ueberschreibt.
        private var geladenesProfil: UserProfileEntity? = null

        var draft by mutableStateOf(SteckbriefUiState())
            private set

        val medikamente: StateFlow<List<MedikamentEntity>> =
            repository.profile
                .filterNotNull()
                .flatMapLatest { repository.observeMedikamente(it.id) }
                .stateIn(viewModelScope, SharingStarted.WhileSubscribed(STOP_TIMEOUT_MILLIS), emptyList())

        val ansprechpartner: StateFlow<List<AnsprechpartnerEntity>> =
            repository.profile
                .filterNotNull()
                .flatMapLatest { repository.observeAnsprechpartner(it.id) }
                .stateIn(viewModelScope, SharingStarted.WhileSubscribed(STOP_TIMEOUT_MILLIS), emptyList())

        init {
            viewModelScope.launch {
                repository.profile.filterNotNull().collect { profil ->
                    geladenesProfil = profil
                    if (!draft.geladen) {
                        draft = profil.zuSteckbriefDraft()
                    }
                }
            }
        }

        fun onDraftChange(update: (SteckbriefUiState) -> SteckbriefUiState) {
            draft = update(draft.copy(gespeichert = false))
        }

        fun speichern() {
            val basis = geladenesProfil ?: return
            viewModelScope.launch {
                repository.updateProfile(basis.mitSteckbriefDraft(draft)).onSuccess {
                    draft = draft.copy(gespeichert = true)
                }
            }
        }

        fun addMedikament(
            name: String,
            dosierung: String,
            seitWann: LocalDate?,
        ) {
            val profileId = geladenesProfil?.id ?: return
            if (name.isBlank()) return
            viewModelScope.launch {
                repository.addMedikament(
                    MedikamentEntity(
                        profileId = profileId,
                        name = name,
                        dosierung = dosierung.ifBlank { null },
                        seitWann = seitWann,
                    ),
                )
            }
        }

        fun deleteMedikament(medikament: MedikamentEntity) {
            viewModelScope.launch { repository.deleteMedikament(medikament) }
        }

        fun addAnsprechpartner(
            rolle: String,
            name: String,
            telefon: String,
        ) {
            val profileId = geladenesProfil?.id ?: return
            if (rolle.isBlank()) return
            viewModelScope.launch {
                repository.addAnsprechpartner(
                    AnsprechpartnerEntity(
                        profileId = profileId,
                        rolle = rolle,
                        name = name.ifBlank { null },
                        telefon = telefon.ifBlank { null },
                    ),
                )
            }
        }

        fun deleteAnsprechpartner(ansprechpartner: AnsprechpartnerEntity) {
            viewModelScope.launch { repository.deleteAnsprechpartner(ansprechpartner) }
        }

        private companion object {
            const val STOP_TIMEOUT_MILLIS = 5_000L
        }
    }
