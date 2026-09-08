package app.schwindeljournal.data.repository

import app.schwindeljournal.data.local.dao.AnsprechpartnerDao
import app.schwindeljournal.data.local.dao.MedikamentDao
import app.schwindeljournal.data.local.dao.UserProfileDao
import app.schwindeljournal.data.local.entity.AnsprechpartnerEntity
import app.schwindeljournal.data.local.entity.MedikamentEntity
import app.schwindeljournal.data.local.entity.UserProfileEntity
import app.schwindeljournal.data.model.Modus
import kotlinx.coroutines.flow.Flow
import timber.log.Timber
import javax.inject.Inject

class UserProfileRepositoryImpl
    @Inject
    constructor(
        private val userProfileDao: UserProfileDao,
        private val medikamentDao: MedikamentDao,
        private val ansprechpartnerDao: AnsprechpartnerDao,
    ) : UserProfileRepository {
        override val profile: Flow<UserProfileEntity?> = userProfileDao.observeProfile()

        override suspend fun createProfile(modus: Modus): Result<Long> =
            runCatching { userProfileDao.insert(UserProfileEntity(modus = modus)) }
                .onFailure { Timber.e(it, "Fehler beim Anlegen des UserProfile (Modus=%s)", modus) }

        override suspend fun updateModus(modus: Modus): Result<Unit> =
            runCatching {
                val bestehendesProfil =
                    userProfileDao.getProfileOnce()
                        ?: error("Kein UserProfile vorhanden – Modus kann nicht gewechselt werden")
                userProfileDao.updateModus(bestehendesProfil.id, modus)
            }.onFailure { Timber.e(it, "Fehler beim Wechseln des Modus auf %s", modus) }

        override suspend fun updateProfile(profile: UserProfileEntity): Result<Unit> =
            runCatching { userProfileDao.update(profile) }
                .onFailure { Timber.e(it, "Fehler beim Speichern des Steckbriefs") }

        override fun observeMedikamente(profileId: Long): Flow<List<MedikamentEntity>> =
            medikamentDao.observeByProfile(profileId)

        override suspend fun addMedikament(medikament: MedikamentEntity): Result<Long> =
            runCatching { medikamentDao.insert(medikament) }
                .onFailure { Timber.e(it, "Fehler beim Anlegen eines Medikaments") }

        override suspend fun deleteMedikament(medikament: MedikamentEntity): Result<Unit> =
            runCatching { medikamentDao.delete(medikament) }
                .onFailure { Timber.e(it, "Fehler beim Loeschen eines Medikaments") }

        override fun observeAnsprechpartner(profileId: Long): Flow<List<AnsprechpartnerEntity>> =
            ansprechpartnerDao.observeByProfile(profileId)

        override suspend fun addAnsprechpartner(ansprechpartner: AnsprechpartnerEntity): Result<Long> =
            runCatching { ansprechpartnerDao.insert(ansprechpartner) }
                .onFailure { Timber.e(it, "Fehler beim Anlegen eines Ansprechpartners") }

        override suspend fun deleteAnsprechpartner(ansprechpartner: AnsprechpartnerEntity): Result<Unit> =
            runCatching { ansprechpartnerDao.delete(ansprechpartner) }
                .onFailure { Timber.e(it, "Fehler beim Loeschen eines Ansprechpartners") }
    }
