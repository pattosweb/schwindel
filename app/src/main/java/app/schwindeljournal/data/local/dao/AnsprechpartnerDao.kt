package app.schwindeljournal.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import app.schwindeljournal.data.local.entity.AnsprechpartnerEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AnsprechpartnerDao {
    @Query("SELECT * FROM ansprechpartner WHERE profileId = :profileId ORDER BY id")
    fun observeByProfile(profileId: Long): Flow<List<AnsprechpartnerEntity>>

    @Insert
    suspend fun insert(ansprechpartner: AnsprechpartnerEntity): Long

    @Delete
    suspend fun delete(ansprechpartner: AnsprechpartnerEntity)
}
