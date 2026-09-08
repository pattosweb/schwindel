package app.schwindeljournal.ui.schnellerfassung

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.schwindeljournal.data.local.entity.JournalEntryEntity
import app.schwindeljournal.data.model.Ampel
import app.schwindeljournal.data.model.Modus
import app.schwindeljournal.data.model.SymptomTyp
import app.schwindeljournal.data.repository.JournalEntryRepository
import app.schwindeljournal.data.repository.SymptomAuswahl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SchnellErfassungViewModel
    @Inject
    constructor(
        private val repository: JournalEntryRepository,
    ) : ViewModel() {
        var uiState by mutableStateOf(SchnellErfassungUiState())
            private set

        fun onAmpelGewaehlt(ampel: Ampel) {
            uiState = uiState.copy(ampel = ampel)
        }

        fun onSituationChange(text: String) {
            uiState = uiState.copy(situation = text)
        }

        fun onTriggerTagAusgewaehlt(tag: String) {
            val neuerText = if (uiState.situation.isBlank()) tag else "${uiState.situation}, $tag"
            uiState = uiState.copy(situation = neuerText)
        }

        fun onKopfNackenPositionChange(text: String) {
            uiState = uiState.copy(kopfNackenPosition = text)
        }

        fun onDauerGewaehlt(sekunden: Int) {
            uiState = uiState.copy(dauerSekunden = sekunden)
        }

        fun onSchlafqualitaetChange(text: String) {
            uiState = uiState.copy(schlafqualitaet = text)
        }

        fun onKissenhoeheChange(text: String) {
            uiState = uiState.copy(kissenhoehe = text)
        }

        fun onTagesbewertungChange(text: String) {
            uiState = uiState.copy(tagesbewertung = text)
        }

        fun onReflexionsfrageChange(text: String) {
            uiState = uiState.copy(reflexionsfrage = text)
        }

        fun onSymptomToggle(typ: SymptomTyp) {
            val aktuelle = uiState.symptome
            uiState = uiState.copy(symptome = if (typ in aktuelle) aktuelle - typ else aktuelle + typ)
        }

        fun onSonstigesSymptomFreitextChange(text: String) {
            uiState = uiState.copy(sonstigesSymptomFreitext = text)
        }

        fun onWarnzeichenCheckboxChange(keinesAufgetreten: Boolean) {
            uiState = uiState.copy(warnzeichenKeinesAufgetreten = keinesAufgetreten)
        }

        fun onDetailsToggle() {
            uiState = uiState.copy(detailsAufgeklappt = !uiState.detailsAufgeklappt)
        }

        fun speichern(modus: Modus) {
            val stand = uiState
            val ampel = stand.ampel ?: return
            viewModelScope.launch {
                val entry = stand.zuEntity(modus, ampel)
                val symptome = stand.zuSymptomAuswahl(modus)
                repository.speichereEintrag(entry, symptome).onSuccess {
                    uiState = SchnellErfassungUiState(zuletztGespeichertUm = stand.uhrzeit)
                }
            }
        }
    }

/** Nur Kompass/Peer fuehren Detail-Nachtrag + Dauer; Quick bleibt auf die Kurznotiz beschraenkt. */
private fun SchnellErfassungUiState.zuEntity(
    modus: Modus,
    ampel: Ampel,
): JournalEntryEntity {
    val detailsErlaubt = modus != Modus.QUICK
    return JournalEntryEntity(
        datum = datum,
        uhrzeit = uhrzeit,
        situation = situation.ifBlank { null },
        kopfNackenPosition = kopfNackenPosition.ifBlank { null }.takeIf { detailsErlaubt },
        dauerSekunden = dauerSekunden.takeIf { detailsErlaubt },
        ampel = ampel,
        schlafqualitaetNachtDavor = schlafqualitaet.ifBlank { null }.takeIf { detailsErlaubt },
        kissenhoeheArt = kissenhoehe.ifBlank { null }.takeIf { detailsErlaubt },
        tagesbewertung = tagesbewertung.ifBlank { null },
        reflexionsfrageAntwort = reflexionsfrage.ifBlank { null }.takeIf { modus == Modus.PEER },
        warnzeichenKeinesAufgetreten = warnzeichenKeinesAufgetreten,
    )
}

private fun SchnellErfassungUiState.zuSymptomAuswahl(modus: Modus): List<SymptomAuswahl> {
    if (modus == Modus.QUICK) return emptyList()
    return symptome.map { typ ->
        val freitext = sonstigesSymptomFreitext.ifBlank { null }.takeIf { typ == SymptomTyp.SONSTIGES }
        SymptomAuswahl(typ = typ, freitext = freitext)
    }
}
