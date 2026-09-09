package app.schwindeljournal.data.repository

import app.schwindeljournal.data.local.entity.ContentBlockEntity
import app.schwindeljournal.data.model.Modus
import app.schwindeljournal.data.model.Sprache
import kotlinx.coroutines.flow.Flow

interface ContentBlockRepository {
    fun observeSichtbareBloecke(
        modus: Modus,
        sprache: Sprache,
    ): Flow<List<ContentBlockEntity>>

    /** Spielt den mit der App ausgelieferten Inhalt ein (REPLACE, siehe ContentSeed). */
    suspend fun seedInhalte(): Result<Unit>
}
