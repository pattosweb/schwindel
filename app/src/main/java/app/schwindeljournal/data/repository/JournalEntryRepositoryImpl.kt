package app.schwindeljournal.data.repository

import androidx.room.withTransaction
import app.schwindeljournal.data.local.AppDatabase
import app.schwindeljournal.data.local.entity.JournalEntryEntity
import app.schwindeljournal.data.local.entity.SymptomEntity
import kotlinx.coroutines.flow.Flow
import timber.log.Timber
import javax.inject.Inject

class JournalEntryRepositoryImpl
    @Inject
    constructor(
        private val database: AppDatabase,
    ) : JournalEntryRepository {
        private val dao = database.journalEntryDao()

        override val alleEintraege: Flow<List<JournalEntryEntity>> = dao.observeAlleEintraege()

        override suspend fun speichereEintrag(
            entry: JournalEntryEntity,
            symptome: List<SymptomAuswahl>,
        ): Result<Long> =
            runCatching {
                database.withTransaction {
                    val entryId = dao.insertEntry(entry)
                    if (symptome.isNotEmpty()) {
                        dao.insertSymptome(
                            symptome.map { SymptomEntity(entryId = entryId, typ = it.typ, freitext = it.freitext) },
                        )
                    }
                    entryId
                }
            }.onFailure { Timber.e(it, "Fehler beim Speichern des Journal-Eintrags") }
    }
