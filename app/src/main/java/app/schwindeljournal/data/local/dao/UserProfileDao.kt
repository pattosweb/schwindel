package app.schwindeljournal.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import app.schwindeljournal.data.local.entity.UserProfileEntity
import app.schwindeljournal.data.model.Modus
import kotlinx.coroutines.flow.Flow

@Dao
interface UserProfileDao {
    // IGNORE statt ABORT: ein doppeltes Anlegen (Doppel-Tap, Retry nach Absturz)
    // erzeugt keine zweite Zeile und wirft keinen Fehler, siehe UserProfileEntity.
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(profile: UserProfileEntity): Long

    @Query("SELECT * FROM user_profile LIMIT 1")
    fun observeProfile(): Flow<UserProfileEntity?>

    @Query("SELECT * FROM user_profile LIMIT 1")
    suspend fun getProfileOnce(): UserProfileEntity?

    @Query("UPDATE user_profile SET modus = :modus WHERE id = :id")
    suspend fun updateModus(
        id: Long,
        modus: Modus,
    )

    @Query("SELECT COUNT(*) FROM user_profile")
    suspend fun countProfiles(): Int

    @Update
    suspend fun update(profile: UserProfileEntity)
}
