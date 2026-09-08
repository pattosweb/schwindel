package app.schwindeljournal.data.local.migration

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

/**
 * Additive Migration (Phase 1): fuegt journal_entry + symptom hinzu, fasst das
 * bestehende Steckbrief-Cluster (user_profile/medikament/ansprechpartner) nicht an.
 * DDL 1:1 aus dem von Room fuer Version 2 exportierten Schema uebernommen
 * (app/schemas/.../2.json), damit sie garantiert zum generierten Ziel-Schema passt.
 */
val MIGRATION_1_2 =
    object : Migration(1, 2) {
        override fun migrate(db: SupportSQLiteDatabase) {
            db.execSQL(
                """
                CREATE TABLE IF NOT EXISTS `journal_entry` (
                    `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                    `datum` TEXT NOT NULL,
                    `uhrzeit` TEXT NOT NULL,
                    `situation` TEXT,
                    `kopfNackenPosition` TEXT,
                    `dauerSekunden` INTEGER,
                    `ampel` TEXT NOT NULL,
                    `schlafqualitaetNachtDavor` TEXT,
                    `kissenhoeheArt` TEXT,
                    `tagesbewertung` TEXT,
                    `reflexionsfrageAntwort` TEXT,
                    `warnzeichenKeinesAufgetreten` INTEGER NOT NULL
                )
                """.trimIndent(),
            )
            db.execSQL(
                """
                CREATE TABLE IF NOT EXISTS `symptom` (
                    `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                    `entryId` INTEGER NOT NULL,
                    `typ` TEXT NOT NULL,
                    `freitext` TEXT,
                    FOREIGN KEY(`entryId`) REFERENCES `journal_entry`(`id`)
                        ON UPDATE NO ACTION ON DELETE CASCADE
                )
                """.trimIndent(),
            )
            db.execSQL(
                "CREATE INDEX IF NOT EXISTS `index_symptom_entryId` ON `symptom` (`entryId`)",
            )
        }
    }

/**
 * Additive Migration (Phase 2): eine neue nullable Spalte fuer das erweiterbare
 * 30/60-Tage-Journalfenster im Quick-Modus (datenmodell-und-content-mapping.md
 * Abschnitt 2), fasst sonst nichts an.
 */
val MIGRATION_2_3 =
    object : Migration(2, 3) {
        override fun migrate(db: SupportSQLiteDatabase) {
            db.execSQL("ALTER TABLE `user_profile` ADD COLUMN `journalFensterErweitert` INTEGER")
        }
    }

/**
 * Additive Migration (Phase 3): legt nur die leere content_block-Tabelle an. Die
 * Befuellung passiert nicht hier, sondern bei jedem App-Start ueber den REPLACE-Seed
 * (siehe ContentBlockEntity-Doku) - so brauchen rein redaktionelle Textaenderungen
 * keine neue Migration.
 */
val MIGRATION_3_4 =
    object : Migration(3, 4) {
        override fun migrate(db: SupportSQLiteDatabase) {
            db.execSQL(
                """
                CREATE TABLE IF NOT EXISTS `content_block` (
                    `id` TEXT NOT NULL,
                    `buchTeil` TEXT NOT NULL,
                    `titel` TEXT NOT NULL,
                    `variantenTiefe` TEXT NOT NULL,
                    `inhaltMarkdown` TEXT NOT NULL,
                    `sichtbarInModus` TEXT NOT NULL,
                    `istWarnzeichenInhalt` INTEGER NOT NULL,
                    PRIMARY KEY(`id`)
                )
                """.trimIndent(),
            )
        }
    }

/** Additive Migration (Phase 4): Erinnerungs-Einstellungen (an/aus + Uhrzeit). */
val MIGRATION_4_5 =
    object : Migration(4, 5) {
        override fun migrate(db: SupportSQLiteDatabase) {
            db.execSQL("ALTER TABLE `user_profile` ADD COLUMN `reminderAktiviert` INTEGER")
            db.execSQL("ALTER TABLE `user_profile` ADD COLUMN `reminderUhrzeit` TEXT")
        }
    }
