package app.schwindeljournal.data.repository

import app.schwindeljournal.data.local.entity.ContentBlockEntity
import app.schwindeljournal.data.model.Modus
import kotlinx.coroutines.flow.Flow

interface ContentBlockRepository {
    fun observeSichtbareBloecke(modus: Modus): Flow<List<ContentBlockEntity>>

    /** Spielt den mit der App ausgelieferten Inhalt ein (REPLACE, siehe ContentSeed). */
    suspend fun seedInhalte(): Result<Unit>
}
