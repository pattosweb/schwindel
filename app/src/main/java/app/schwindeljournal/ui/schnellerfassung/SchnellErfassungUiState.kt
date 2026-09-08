package app.schwindeljournal.ui.schnellerfassung

import app.schwindeljournal.data.model.Ampel
import app.schwindeljournal.data.model.SymptomTyp
import java.time.LocalDate
import java.time.LocalTime

data class SchnellErfassungUiState(
    // Absichtlich beim Oeffnen des Formulars fixiert (nicht erst beim Speichern) –
    // der Zeitpunkt des Vorfalls ist relevant, nicht der (evtl. spaetere) Ausfuellzeitpunkt.
    val datum: LocalDate = LocalDate.now(),
    val uhrzeit: LocalTime = LocalTime.now(),
    val ampel: Ampel? = null,
    val situation: String = "",
    val kopfNackenPosition: String = "",
    val dauerSekunden: Int? = null,
    val schlafqualitaet: String = "",
    val kissenhoehe: String = "",
    val tagesbewertung: String = "",
    val reflexionsfrage: String = "",
    val symptome: Set<SymptomTyp> = emptySet(),
    val sonstigesSymptomFreitext: String = "",
    val warnzeichenKeinesAufgetreten: Boolean = true,
    val detailsAufgeklappt: Boolean = false,
    val zuletztGespeichertUm: LocalTime? = null,
) {
    val kannGespeichertWerden: Boolean get() = ampel != null
}
