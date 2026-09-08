package app.schwindeljournal.data.repository

import app.schwindeljournal.data.local.entity.UserProfileEntity
import app.schwindeljournal.data.model.Modus
import kotlinx.coroutines.flow.Flow

/**
 * Einziger Zugriffspunkt auf das UserProfile (kein direkter DAO-Zugriff aus der UI) –
 * Grundlage fuer die zentrale Modus-Abfrage (siehe roadmap-schwindeljournal-app.md,
 * Phase 0.5).
 */
interface UserProfileRepository {
    val profile: Flow<UserProfileEntity?>

    suspend fun createProfile(modus: Modus): Result<Long>

    suspend fun updateModus(modus: Modus): Result<Unit>
}
