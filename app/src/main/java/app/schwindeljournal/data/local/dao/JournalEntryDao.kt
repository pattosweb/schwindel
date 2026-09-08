package app.schwindeljournal.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import app.schwindeljournal.data.local.entity.JournalEntryEntity
import app.schwindeljournal.data.local.entity.SymptomEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface JournalEntryDao {
    @Insert
    suspend fun insertEntry(entry: JournalEntryEntity): Long

    @Insert
    suspend fun insertSymptome(symptome: List<SymptomEntity>)

    @Query("SELECT * FROM journal_entry ORDER BY datum DESC, uhrzeit DESC")
    fun observeAlleEintraege(): Flow<List<JournalEntryEntity>>

    @Query("SELECT * FROM symptom WHERE entryId = :entryId")
    suspend fun getSymptomeFuerEintrag(entryId: Long): List<SymptomEntity>

    @Delete
    suspend fun deleteEntry(entry: JournalEntryEntity)
}
