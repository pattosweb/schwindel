package app.schwindeljournal.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import app.schwindeljournal.data.local.entity.MedikamentEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MedikamentDao {
    @Query("SELECT * FROM medikament WHERE profileId = :profileId ORDER BY id")
    fun observeByProfile(profileId: Long): Flow<List<MedikamentEntity>>

    @Insert
    suspend fun insert(medikament: MedikamentEntity): Long

    @Delete
    suspend fun delete(medikament: MedikamentEntity)
}
