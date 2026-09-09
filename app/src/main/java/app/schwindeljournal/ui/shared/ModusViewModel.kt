package app.schwindeljournal.ui.shared

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.schwindeljournal.data.model.Modus
import app.schwindeljournal.data.model.Sprache
import app.schwindeljournal.data.model.resolveEffektiveSprache
import app.schwindeljournal.data.repository.UserProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.Locale
import javax.inject.Inject

/**
 * Einzige, zentrale Quelle des aktuellen Modus fuer die gesamte App (siehe
 * roadmap-schwindeljournal-app.md, Phase 0.5: "zentrale Modus-Abfrage, nicht
 * verstreut in jedem Screen einzeln"). Wird einmal auf Activity-Ebene erzeugt
 * und explizit an Screens weitergereicht statt pro Screen neu abgefragt.
 */
@HiltViewModel
class ModusViewModel
    @Inject
    constructor(
        private val repository: UserProfileRepository,
    ) : ViewModel() {
        /** null = noch unbekannt (Room noch nicht geantwortet), sonst true/false. */
        val hasProfile: StateFlow<Boolean?> =
            repository.profile
                .map { it != null }
                .stateIn(viewModelScope, SharingStarted.WhileSubscribed(STOP_TIMEOUT_MILLIS), null)

        val modus: StateFlow<Modus?> =
            repository.profile
                .map { it?.modus }
                .stateIn(viewModelScope, SharingStarted.WhileSubscribed(STOP_TIMEOUT_MILLIS), null)

        // Barrierefreiheit (Phase 4 Rest): zentral wie der Modus, statt in jedem Screen
        // einzeln aus dem Profil abzufragen (gleiches Prinzip wie oben bei [modus]).
        val ampelHoherKontrast: StateFlow<Boolean> =
            repository.profile
                .map { it?.ampelHoherKontrast == true }
                .stateIn(viewModelScope, SharingStarted.WhileSubscribed(STOP_TIMEOUT_MILLIS), false)

        // Mehrsprachigkeit (Phase 5): zentral wie Modus/Ampel-Kontrast. Greift schon
        // waehrend des Onboardings (bevor ein Profil existiert), damit auch der
        // Erststart in der Systemsprache erscheint, falls verfuegbar.
        private val systemSprachCode = Locale.getDefault().language

        val sprache: StateFlow<Sprache> =
            repository.profile
                .map { resolveEffektiveSprache(it?.sprache, systemSprachCode) }
                .stateIn(
                    viewModelScope,
                    SharingStarted.WhileSubscribed(STOP_TIMEOUT_MILLIS),
                    resolveEffektiveSprache(null, systemSprachCode),
                )

        fun onModusGewaehlt(modus: Modus) {
            viewModelScope.launch { repository.createProfile(modus) }
        }

        fun onModusGewechselt(modus: Modus) {
            viewModelScope.launch { repository.updateModus(modus) }
        }

        private companion object {
            const val STOP_TIMEOUT_MILLIS = 5_000L
        }
    }
