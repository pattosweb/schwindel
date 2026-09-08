package app.schwindeljournal.ui.steckbrief

import app.schwindeljournal.data.local.entity.UserProfileEntity
import java.time.LocalDate

data class SteckbriefUiState(
    val geladen: Boolean = false,
    val geburtsjahr: Int? = null,
    val berufMitBelastung: Boolean? = null,
    val vorerkrankungen: String = "",
    val fruehereVerletzungen: String = "",
    val ersterVorfall: Boolean? = null,
    val seitWannWiederkehrend: String = "",
    val letzterBlutdruck: String = "",
    val letzterBlutdruckDatum: LocalDate? = null,
    val gespeichert: Boolean = false,
)

fun UserProfileEntity.zuSteckbriefDraft(): SteckbriefUiState =
    SteckbriefUiState(
        geladen = true,
        geburtsjahr = geburtsjahr,
        berufMitBelastung = berufMitBelastung,
        vorerkrankungen = vorerkrankungen.orEmpty(),
        fruehereVerletzungen = fruehereVerletzungenKopfNacken.orEmpty(),
        ersterVorfall = ersterVorfall,
        seitWannWiederkehrend = seitWannWiederkehrend.orEmpty(),
        letzterBlutdruck = letzterBlutdruck.orEmpty(),
        letzterBlutdruckDatum = letzterBlutdruckDatum,
    )

fun UserProfileEntity.mitSteckbriefDraft(draft: SteckbriefUiState): UserProfileEntity =
    copy(
        geburtsjahr = draft.geburtsjahr,
        berufMitBelastung = draft.berufMitBelastung,
        vorerkrankungen = draft.vorerkrankungen.ifBlank { null },
        fruehereVerletzungenKopfNacken = draft.fruehereVerletzungen.ifBlank { null },
        ersterVorfall = draft.ersterVorfall,
        seitWannWiederkehrend = draft.seitWannWiederkehrend.ifBlank { null },
        letzterBlutdruck = draft.letzterBlutdruck.ifBlank { null },
        letzterBlutdruckDatum = draft.letzterBlutdruckDatum,
    )
