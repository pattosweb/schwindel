package app.schwindeljournal.data.repository

import app.schwindeljournal.data.local.dao.UserProfileDao
import app.schwindeljournal.data.local.entity.UserProfileEntity
import app.schwindeljournal.data.model.Modus
import kotlinx.coroutines.flow.Flow
import timber.log.Timber
import javax.inject.Inject

class UserProfileRepositoryImpl
    @Inject
    constructor(
        private val dao: UserProfileDao,
    ) : UserProfileRepository {
        override val profile: Flow<UserProfileEntity?> = dao.observeProfile()

        override suspend fun createProfile(modus: Modus): Result<Long> =
            runCatching { dao.insert(UserProfileEntity(modus = modus)) }
                .onFailure { Timber.e(it, "Fehler beim Anlegen des UserProfile (Modus=%s)", modus) }

        override suspend fun updateModus(modus: Modus): Result<Unit> =
            runCatching {
                val bestehendesProfil =
                    dao.getProfileOnce()
                        ?: error("Kein UserProfile vorhanden – Modus kann nicht gewechselt werden")
                dao.updateModus(bestehendesProfil.id, modus)
            }.onFailure { Timber.e(it, "Fehler beim Wechseln des Modus auf %s", modus) }
    }
