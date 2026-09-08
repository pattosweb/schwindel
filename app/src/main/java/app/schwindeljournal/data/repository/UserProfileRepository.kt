package app.schwindeljournal.data.repository

import app.schwindeljournal.data.local.entity.AnsprechpartnerEntity
import app.schwindeljournal.data.local.entity.MedikamentEntity
import app.schwindeljournal.data.local.entity.UserProfileEntity
import app.schwindeljournal.data.model.Modus
import kotlinx.coroutines.flow.Flow

/**
 * Einziger Zugriffspunkt auf UserProfile + Steckbrief-Cluster (Medikamente,
 * Ansprechpartner) – kein direkter DAO-Zugriff aus der UI. Grundlage fuer die
 * zentrale Modus-Abfrage (Phase 0.5) und den Steckbrief (Phase 0.5-Nachtrag,
 * siehe PROJECT_LOG.md "Bekannte Luecken" vom 08.09.2026).
 */
interface UserProfileRepository {
    val profile: Flow<UserProfileEntity?>

    suspend fun createProfile(modus: Modus): Result<Long>

    suspend fun updateModus(modus: Modus): Result<Unit>

    /** Schreibt den kompletten Steckbrief (Person/Vorerkrankungen/Blutdruck) auf einmal. */
    suspend fun updateProfile(profile: UserProfileEntity): Result<Unit>

    fun observeMedikamente(profileId: Long): Flow<List<MedikamentEntity>>

    suspend fun addMedikament(medikament: MedikamentEntity): Result<Long>

    suspend fun deleteMedikament(medikament: MedikamentEntity): Result<Unit>

    fun observeAnsprechpartner(profileId: Long): Flow<List<AnsprechpartnerEntity>>

    suspend fun addAnsprechpartner(ansprechpartner: AnsprechpartnerEntity): Result<Long>

    suspend fun deleteAnsprechpartner(ansprechpartner: AnsprechpartnerEntity): Result<Unit>
}
