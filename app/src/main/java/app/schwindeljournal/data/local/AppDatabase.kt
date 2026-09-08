package app.schwindeljournal.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import app.schwindeljournal.data.local.converter.Converters
import app.schwindeljournal.data.local.dao.AnsprechpartnerDao
import app.schwindeljournal.data.local.dao.JournalEntryDao
import app.schwindeljournal.data.local.dao.MedikamentDao
import app.schwindeljournal.data.local.dao.UserProfileDao
import app.schwindeljournal.data.local.entity.AnsprechpartnerEntity
import app.schwindeljournal.data.local.entity.JournalEntryEntity
import app.schwindeljournal.data.local.entity.MedikamentEntity
import app.schwindeljournal.data.local.entity.SymptomEntity
import app.schwindeljournal.data.local.entity.UserProfileEntity

/**
 * Version 1: Steckbrief-Cluster (Phase 0.5). Version 2: JournalEntry/Symptom
 * (Phase 1, additive Migration, siehe [app.schwindeljournal.data.local.migration.MIGRATION_1_2])
 * – bestehende Steckbrief-Daten bleiben beim Upgrade unangetastet.
 */
@Database(
    entities = [
        UserProfileEntity::class,
        MedikamentEntity::class,
        AnsprechpartnerEntity::class,
        JournalEntryEntity::class,
        SymptomEntity::class,
    ],
    version = 2,
    exportSchema = true,
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userProfileDao(): UserProfileDao

    abstract fun journalEntryDao(): JournalEntryDao

    abstract fun medikamentDao(): MedikamentDao

    abstract fun ansprechpartnerDao(): AnsprechpartnerDao
}
