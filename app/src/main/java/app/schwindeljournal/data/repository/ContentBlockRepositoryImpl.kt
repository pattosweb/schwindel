package app.schwindeljournal.data.repository

import app.schwindeljournal.data.local.dao.ContentBlockDao
import app.schwindeljournal.data.local.entity.ContentBlockEntity
import app.schwindeljournal.data.model.Modus
import app.schwindeljournal.data.seed.ContentSeed
import kotlinx.coroutines.flow.Flow
import timber.log.Timber
import javax.inject.Inject

class ContentBlockRepositoryImpl
    @Inject
    constructor(
        private val dao: ContentBlockDao,
    ) : ContentBlockRepository {
        override fun observeSichtbareBloecke(modus: Modus): Flow<List<ContentBlockEntity>> =
            dao.observeSichtbareBloecke(modus.name)

        override suspend fun seedInhalte(): Result<Unit> =
            runCatching { dao.ersetzeAlle(ContentSeed.alleBloecke) }
                .onFailure { Timber.e(it, "Fehler beim Einspielen der Wissens-Bibliothek-Inhalte") }
    }
