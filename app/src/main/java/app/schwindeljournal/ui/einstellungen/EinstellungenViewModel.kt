package app.schwindeljournal.ui.einstellungen

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.schwindeljournal.data.local.entity.UserProfileEntity
import app.schwindeljournal.data.repository.UserProfileRepository
import app.schwindeljournal.work.ReminderScheduler
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalTime
import javax.inject.Inject

private val STANDARD_ERINNERUNGSZEIT: LocalTime = LocalTime.of(20, 0)
private const val STOP_TIMEOUT_MILLIS = 5_000L

@HiltViewModel
class EinstellungenViewModel
    @Inject
    constructor(
        private val profileRepository: UserProfileRepository,
        @ApplicationContext private val context: Context,
    ) : ViewModel() {
        val profil: StateFlow<UserProfileEntity?> =
            profileRepository.profile
                .stateIn(viewModelScope, SharingStarted.WhileSubscribed(STOP_TIMEOUT_MILLIS), null)

        fun onErinnerungAktivChange(aktiv: Boolean) {
            val aktuelles = profil.value ?: return
            viewModelScope.launch {
                profileRepository.updateProfile(aktuelles.copy(reminderAktiviert = aktiv))
                if (aktiv) {
                    ReminderScheduler.planeFuer(context, aktuelles.reminderUhrzeit ?: STANDARD_ERINNERUNGSZEIT)
                } else {
                    ReminderScheduler.abbrechen(context)
                }
            }
        }

        fun onErinnerungsUhrzeitChange(uhrzeit: LocalTime) {
            val aktuelles = profil.value ?: return
            viewModelScope.launch {
                profileRepository.updateProfile(aktuelles.copy(reminderUhrzeit = uhrzeit))
                if (aktuelles.reminderAktiviert == true) {
                    ReminderScheduler.planeFuer(context, uhrzeit)
                }
            }
        }
    }
