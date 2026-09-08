package app.schwindeljournal.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import app.schwindeljournal.data.local.entity.ContentBlockEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ContentBlockDao {
    // REPLACE: der Seed laeuft bei jedem App-Start erneut, damit redaktionelle
    // Aenderungen ohne neue Migration ankommen (siehe ContentBlockEntity-Doku).
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun ersetzeAlle(bloecke: List<ContentBlockEntity>)

    @Query(
        "SELECT * FROM content_block WHERE istWarnzeichenInhalt = 1 " +
            "OR sichtbarInModus LIKE '%' || :modus || '%' " +
            "ORDER BY istWarnzeichenInhalt DESC, buchTeil ASC",
    )
    fun observeSichtbareBloecke(modus: String): Flow<List<ContentBlockEntity>>
}
