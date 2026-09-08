package app.schwindeljournal.data.repository

import app.schwindeljournal.data.local.entity.JournalEntryEntity
import app.schwindeljournal.data.local.entity.SymptomEntity
import app.schwindeljournal.data.model.SymptomTyp
import kotlinx.coroutines.flow.Flow

interface JournalEntryRepository {
    val alleEintraege: Flow<List<JournalEntryEntity>>
    val alleSymptome: Flow<List<SymptomEntity>>

    suspend fun speichereEintrag(
        entry: JournalEntryEntity,
        symptome: List<SymptomAuswahl>,
    ): Result<Long>
}

/** Begleitsymptom-Auswahl aus der UI, noch ohne bekannte entryId (die entsteht erst beim Insert). */
data class SymptomAuswahl(
    val typ: SymptomTyp,
    val freitext: String? = null,
)
