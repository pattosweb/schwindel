package app.schwindeljournal.ui.journalverlauf

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.schwindeljournal.data.local.entity.AnsprechpartnerEntity
import app.schwindeljournal.data.local.entity.JournalEntryEntity
import app.schwindeljournal.data.local.entity.MedikamentEntity
import app.schwindeljournal.data.local.entity.SymptomEntity
import app.schwindeljournal.data.local.entity.UserProfileEntity
import app.schwindeljournal.data.repository.JournalEntryRepository
import app.schwindeljournal.data.repository.UserProfileRepository
import app.schwindeljournal.pdf.JournalExportDaten
import app.schwindeljournal.pdf.JournalPdfExporter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

sealed interface PdfExportStatus {
    data object Idle : PdfExportStatus

    data object Laeuft : PdfExportStatus

    data class Fertig(
        val uri: Uri,
    ) : PdfExportStatus

    data object Fehler : PdfExportStatus
}

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class JournalVerlaufViewModel
    @Inject
    constructor(
        private val journalRepository: JournalEntryRepository,
        private val profileRepository: UserProfileRepository,
        private val pdfExporter: JournalPdfExporter,
    ) : ViewModel() {
        val eintraege: StateFlow<List<JournalEntryEntity>> =
            journalRepository.alleEintraege
                .stateIn(viewModelScope, SharingStarted.WhileSubscribed(STOP_TIMEOUT_MILLIS), emptyList())

        val symptome: StateFlow<List<SymptomEntity>> =
            journalRepository.alleSymptome
                .stateIn(viewModelScope, SharingStarted.WhileSubscribed(STOP_TIMEOUT_MILLIS), emptyList())

        val profil: StateFlow<UserProfileEntity?> =
            profileRepository.profile
                .stateIn(viewModelScope, SharingStarted.WhileSubscribed(STOP_TIMEOUT_MILLIS), null)

        private val medikamente: StateFlow<List<MedikamentEntity>> =
            profileRepository.profile
                .filterNotNull()
                .flatMapLatest { profileRepository.observeMedikamente(it.id) }
                .stateIn(viewModelScope, SharingStarted.WhileSubscribed(STOP_TIMEOUT_MILLIS), emptyList())

        private val ansprechpartner: StateFlow<List<AnsprechpartnerEntity>> =
            profileRepository.profile
                .filterNotNull()
                .flatMapLatest { profileRepository.observeAnsprechpartner(it.id) }
                .stateIn(viewModelScope, SharingStarted.WhileSubscribed(STOP_TIMEOUT_MILLIS), emptyList())

        var pdfExportStatus by mutableStateOf<PdfExportStatus>(PdfExportStatus.Idle)
            private set

        fun onJournalFensterErweitertToggle(erweitert: Boolean) {
            val aktuell = profil.value ?: return
            viewModelScope.launch {
                profileRepository.updateProfile(aktuell.copy(journalFensterErweitert = erweitert))
            }
        }

        fun exportierePdf() {
            val aktuellesProfil = profil.value ?: return
            pdfExportStatus = PdfExportStatus.Laeuft
            viewModelScope.launch {
                runCatching {
                    val musterHinweise =
                        berechneSymptomMuster(eintraege.value, symptome.value) +
                            berechneTriggerMuster(eintraege.value)
                    pdfExporter.exportiere(
                        JournalExportDaten(
                            profil = aktuellesProfil,
                            medikamente = medikamente.value,
                            ansprechpartner = ansprechpartner.value,
                            eintraege = eintraege.value,
                            symptome = symptome.value,
                            musterHinweise = musterHinweise,
                        ),
                    )
                }.onSuccess { uri -> pdfExportStatus = PdfExportStatus.Fertig(uri) }
                    .onFailure {
                        Timber.e(it, "PDF-Export fehlgeschlagen")
                        pdfExportStatus = PdfExportStatus.Fehler
                    }
            }
        }

        fun pdfExportStatusZurueckgesetzt() {
            pdfExportStatus = PdfExportStatus.Idle
        }

        private companion object {
            const val STOP_TIMEOUT_MILLIS = 5_000L
        }
    }
