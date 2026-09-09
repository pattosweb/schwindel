package app.schwindeljournal.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import app.schwindeljournal.data.local.converter.Converters
import app.schwindeljournal.data.local.converter.DateTimeConverters
import app.schwindeljournal.data.local.converter.SpracheConverter
import app.schwindeljournal.data.local.dao.AnsprechpartnerDao
import app.schwindeljournal.data.local.dao.ContentBlockDao
import app.schwindeljournal.data.local.dao.JournalEntryDao
import app.schwindeljournal.data.local.dao.MedikamentDao
import app.schwindeljournal.data.local.dao.UserProfileDao
import app.schwindeljournal.data.local.entity.AnsprechpartnerEntity
import app.schwindeljournal.data.local.entity.ContentBlockEntity
import app.schwindeljournal.data.local.entity.JournalEntryEntity
import app.schwindeljournal.data.local.entity.MedikamentEntity
import app.schwindeljournal.data.local.entity.SymptomEntity
import app.schwindeljournal.data.local.entity.UserProfileEntity

/**
 * Version 1: Steckbrief-Cluster (Phase 0.5). Version 2: JournalEntry/Symptom
 * (Phase 1, siehe [app.schwindeljournal.data.local.migration.MIGRATION_1_2]). Version 3:
 * UserProfile.journalFensterErweitert (Phase 2, siehe MIGRATION_2_3). Version 4:
 * ContentBlock-Tabelle (Phase 3, siehe MIGRATION_3_4). Version 5: Erinnerungs-
 * Einstellungen (Phase 4, siehe MIGRATION_4_5). Version 6: Ampel-Kontrast-Einstellung
 * (Phase 4 Rest, siehe MIGRATION_5_6). Version 7: Mehrsprachigkeit (Phase 5, siehe
 * MIGRATION_6_7) - jede Migration additiv, bestehende Daten bleiben beim Upgrade
 * unangetastet.
 */
@Database(
    entities = [
        UserProfileEntity::class,
        MedikamentEntity::class,
        AnsprechpartnerEntity::class,
        JournalEntryEntity::class,
        SymptomEntity::class,
        ContentBlockEntity::class,
    ],
    version = 7,
    exportSchema = true,
)
@TypeConverters(Converters::class, DateTimeConverters::class, SpracheConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userProfileDao(): UserProfileDao

    abstract fun journalEntryDao(): JournalEntryDao

    abstract fun medikamentDao(): MedikamentDao

    abstract fun ansprechpartnerDao(): AnsprechpartnerDao

    abstract fun contentBlockDao(): ContentBlockDao
}
