package app.schwindeljournal.data.local.migration

import androidx.room.testing.MigrationTestHelper
import androidx.sqlite.db.framework.FrameworkSQLiteOpenHelperFactory
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import app.schwindeljournal.data.local.AppDatabase
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Adversariale QA fuer die erste echte Room-Migration (Arbeitsweise-Regel 2,
 * CLAUDE.md): stellt eine echte Version-1-Datenbank her (mit einem bereits
 * ausgefuellten Steckbrief), fuehrt MIGRATION_1_2 aus und prueft, dass dabei
 * kein Datenverlust am bestehenden Steckbrief entsteht UND die neuen Tabellen
 * nutzbar sind.
 */
@RunWith(AndroidJUnit4::class)
class AppDatabaseMigrationTest {
    private val dbName = "migration-test"

    @get:Rule
    val helper: MigrationTestHelper =
        MigrationTestHelper(
            InstrumentationRegistry.getInstrumentation(),
            AppDatabase::class.java,
            emptyList(),
            FrameworkSQLiteOpenHelperFactory(),
        )

    @Test
    fun migration1Zu2ErhaeltBestehendenSteckbriefUndLegtNeueTabellenAn() {
        // Schritt 1: echte Version-1-DB mit einem bestehenden UserProfile anlegen
        // (so, wie sie auf einem Geraet vor dem App-Update aussehen wuerde).
        helper.createDatabase(dbName, 1).apply {
            execSQL(
                "INSERT INTO user_profile (id, modus, geburtsjahr) VALUES (1, 'PEER', 1985)",
            )
            close()
        }

        // Schritt 2: Migration ausfuehren (validiert automatisch gegen das
        // exportierte Ziel-Schema aus app/schemas/).
        val migriert = helper.runMigrationsAndValidate(dbName, 2, true, MIGRATION_1_2)

        val profilCursor = migriert.query("SELECT modus, geburtsjahr FROM user_profile WHERE id = 1")
        assertEquals(1, profilCursor.count)
        profilCursor.moveToFirst()
        assertEquals("PEER", profilCursor.getString(0))
        assertEquals(1985, profilCursor.getInt(1))
        profilCursor.close()

        // Neue Tabellen sind da und benutzbar (Insert klappt, FK/Index vorhanden).
        migriert.execSQL(
            "INSERT INTO journal_entry (id, datum, uhrzeit, ampel, warnzeichenKeinesAufgetreten) " +
                "VALUES (1, '2026-09-08', '10:00', 'GRUEN', 1)",
        )
        migriert.execSQL(
            "INSERT INTO symptom (id, entryId, typ) VALUES (1, 1, 'KOPFSCHMERZ')",
        )
        val symptomCursor = migriert.query("SELECT COUNT(*) FROM symptom WHERE entryId = 1")
        symptomCursor.moveToFirst()
        assertEquals(1, symptomCursor.getInt(0))
        symptomCursor.close()
        migriert.close()
    }

    @Test
    fun migration2Zu3ErhaeltBestehendenJournalEintragUndErgaenztNeueSpalte() {
        // Version-2-DB mit UserProfile + einem Journal-Eintrag anlegen.
        helper.createDatabase(dbName, 2).apply {
            execSQL("INSERT INTO user_profile (id, modus) VALUES (1, 'QUICK')")
            execSQL(
                "INSERT INTO journal_entry (id, datum, uhrzeit, ampel, warnzeichenKeinesAufgetreten) " +
                    "VALUES (1, '2026-09-08', '10:00', 'ROT', 0)",
            )
            close()
        }

        val migriert = helper.runMigrationsAndValidate(dbName, 3, true, MIGRATION_2_3)

        // Bestehender Journal-Eintrag unangetastet.
        val eintragCursor = migriert.query("SELECT ampel, warnzeichenKeinesAufgetreten FROM journal_entry WHERE id = 1")
        assertEquals(1, eintragCursor.count)
        eintragCursor.moveToFirst()
        assertEquals("ROT", eintragCursor.getString(0))
        assertEquals(0, eintragCursor.getInt(1))
        eintragCursor.close()

        // Neue Spalte ist da (NULL fuer Bestandsdaten) und beschreibbar.
        val neueSpalteCursor = migriert.query("SELECT journalFensterErweitert FROM user_profile WHERE id = 1")
        neueSpalteCursor.moveToFirst()
        assertEquals(true, neueSpalteCursor.isNull(0))
        neueSpalteCursor.close()

        migriert.execSQL("UPDATE user_profile SET journalFensterErweitert = 1 WHERE id = 1")
        val aktualisiertCursor = migriert.query("SELECT journalFensterErweitert FROM user_profile WHERE id = 1")
        aktualisiertCursor.moveToFirst()
        assertEquals(1, aktualisiertCursor.getInt(0))
        aktualisiertCursor.close()
        migriert.close()
    }

    @Test
    fun migration3Zu4ErhaeltBestandUndLegtContentBlockTabelleNutzbarAn() {
        // Version-3-DB mit UserProfile anlegen (JournalEntry/Symptom bleiben aussen vor,
        // reicht fuer den Nachweis "bestehende Tabellen unangetastet").
        helper.createDatabase(dbName, 3).apply {
            execSQL("INSERT INTO user_profile (id, modus, journalFensterErweitert) VALUES (1, 'KOMPASS', 1)")
            close()
        }

        val migriert = helper.runMigrationsAndValidate(dbName, 4, true, MIGRATION_3_4)

        val profilCursor = migriert.query("SELECT modus, journalFensterErweitert FROM user_profile WHERE id = 1")
        profilCursor.moveToFirst()
        assertEquals("KOMPASS", profilCursor.getString(0))
        assertEquals(1, profilCursor.getInt(1))
        profilCursor.close()

        migriert.execSQL(
            "INSERT INTO content_block " +
                "(id, buchTeil, titel, variantenTiefe, inhaltMarkdown, sichtbarInModus, istWarnzeichenInhalt) " +
                "VALUES ('teilA-warnzeichen', 'A', 'Test', 'VOLL', 'x', 'KOMPASS,PEER,QUICK', 1)",
        )
        val contentCursor = migriert.query("SELECT COUNT(*) FROM content_block")
        contentCursor.moveToFirst()
        assertEquals(1, contentCursor.getInt(0))
        contentCursor.close()
        migriert.close()
    }

    @Test
    fun migration4Zu5ErhaeltBestandUndErgaenztErinnerungsSpalten() {
        helper.createDatabase(dbName, 4).apply {
            execSQL("INSERT INTO user_profile (id, modus, geburtsjahr) VALUES (1, 'PEER', 1990)")
            close()
        }

        val migriert = helper.runMigrationsAndValidate(dbName, 5, true, MIGRATION_4_5)

        val cursor =
            migriert.query(
                "SELECT modus, geburtsjahr, reminderAktiviert, reminderUhrzeit FROM user_profile WHERE id = 1",
            )
        cursor.moveToFirst()
        assertEquals("PEER", cursor.getString(0))
        assertEquals(1990, cursor.getInt(1))
        assertEquals(true, cursor.isNull(2))
        assertEquals(true, cursor.isNull(3))
        cursor.close()

        migriert.execSQL("UPDATE user_profile SET reminderAktiviert = 1, reminderUhrzeit = '20:00' WHERE id = 1")
        val aktualisiert = migriert.query("SELECT reminderAktiviert, reminderUhrzeit FROM user_profile WHERE id = 1")
        aktualisiert.moveToFirst()
        assertEquals(1, aktualisiert.getInt(0))
        assertEquals("20:00", aktualisiert.getString(1))
        aktualisiert.close()
        migriert.close()
    }

    @Test
    fun migration5Zu6ErhaeltBestandUndErgaenztAmpelKontrastSpalte() {
        helper.createDatabase(dbName, 5).apply {
            execSQL(
                "INSERT INTO user_profile (id, modus, geburtsjahr, reminderAktiviert, reminderUhrzeit) " +
                    "VALUES (1, 'KOMPASS', 1978, 1, '20:00')",
            )
            close()
        }

        val migriert = helper.runMigrationsAndValidate(dbName, 6, true, MIGRATION_5_6)

        val cursor =
            migriert.query(
                "SELECT modus, geburtsjahr, reminderAktiviert, reminderUhrzeit, ampelHoherKontrast " +
                    "FROM user_profile WHERE id = 1",
            )
        cursor.moveToFirst()
        assertEquals("KOMPASS", cursor.getString(0))
        assertEquals(1978, cursor.getInt(1))
        assertEquals(1, cursor.getInt(2))
        assertEquals("20:00", cursor.getString(3))
        assertEquals(true, cursor.isNull(4))
        cursor.close()

        migriert.execSQL("UPDATE user_profile SET ampelHoherKontrast = 1 WHERE id = 1")
        val aktualisiert = migriert.query("SELECT ampelHoherKontrast FROM user_profile WHERE id = 1")
        aktualisiert.moveToFirst()
        assertEquals(1, aktualisiert.getInt(0))
        aktualisiert.close()
        migriert.close()
    }

    @Test
    fun migration6Zu7ErhaeltBestandUndErgaenztSpracheSpalten() {
        helper.createDatabase(dbName, 6).apply {
            execSQL(
                "INSERT INTO user_profile (id, modus, geburtsjahr, ampelHoherKontrast) " +
                    "VALUES (1, 'QUICK', 2001, 1)",
            )
            execSQL(
                "INSERT INTO content_block " +
                    "(id, buchTeil, titel, variantenTiefe, inhaltMarkdown, sichtbarInModus, istWarnzeichenInhalt) " +
                    "VALUES ('teilA-warnzeichen', 'A', 'Test', 'VOLL', 'x', 'KOMPASS,PEER,QUICK', 1)",
            )
            close()
        }

        val migriert = helper.runMigrationsAndValidate(dbName, 7, true, MIGRATION_6_7)

        // Bestehendes Profil und bestehender ContentBlock unangetastet, sprache-Spalten
        // wie erwartet: user_profile.sprache NULL ("folge Systemsprache"), content_block
        // .sprache DEFAULT 'DE' fuer die Bestandszeile.
        val profilCursor = migriert.query("SELECT modus, geburtsjahr, ampelHoherKontrast, sprache FROM user_profile")
        profilCursor.moveToFirst()
        assertEquals("QUICK", profilCursor.getString(0))
        assertEquals(2001, profilCursor.getInt(1))
        assertEquals(1, profilCursor.getInt(2))
        assertEquals(true, profilCursor.isNull(3))
        profilCursor.close()

        val contentCursor = migriert.query("SELECT titel, sprache FROM content_block WHERE id = 'teilA-warnzeichen'")
        contentCursor.moveToFirst()
        assertEquals("Test", contentCursor.getString(0))
        assertEquals("DE", contentCursor.getString(1))
        contentCursor.close()

        migriert.execSQL("UPDATE user_profile SET sprache = 'EN' WHERE id = 1")
        migriert.execSQL(
            "INSERT INTO content_block " +
                "(id, buchTeil, titel, variantenTiefe, inhaltMarkdown, sichtbarInModus, " +
                "istWarnzeichenInhalt, sprache) " +
                "VALUES ('teilA-warnzeichen-en', 'A', 'Test EN', 'VOLL', 'x', 'KOMPASS,PEER,QUICK', 1, 'EN')",
        )
        val nachUpdate = migriert.query("SELECT COUNT(*) FROM content_block WHERE sprache = 'EN'")
        nachUpdate.moveToFirst()
        assertEquals(1, nachUpdate.getInt(0))
        nachUpdate.close()
        migriert.close()
    }
}
