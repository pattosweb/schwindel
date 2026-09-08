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
}
