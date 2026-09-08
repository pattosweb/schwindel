package app.schwindeljournal.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import app.schwindeljournal.data.local.converter.Converters
import app.schwindeljournal.data.local.dao.UserProfileDao
import app.schwindeljournal.data.local.entity.AnsprechpartnerEntity
import app.schwindeljournal.data.local.entity.MedikamentEntity
import app.schwindeljournal.data.local.entity.UserProfileEntity

/**
 * Version 1: Steckbrief-Cluster (UserProfile/Medikament/Ansprechpartner) fuer
 * Onboarding und Modus-Verwaltung (Phase 0.5). JournalEntry/Symptom/ContentBlock
 * folgen als additive Migrationen in Phase 1/3 (siehe roadmap-schwindeljournal-app.md).
 */
@Database(
    entities = [
        UserProfileEntity::class,
        MedikamentEntity::class,
        AnsprechpartnerEntity::class,
    ],
    version = 1,
    exportSchema = true,
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userProfileDao(): UserProfileDao
}
