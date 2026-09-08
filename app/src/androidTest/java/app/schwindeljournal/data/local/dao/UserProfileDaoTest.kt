package app.schwindeljournal.data.local.dao

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import app.schwindeljournal.data.local.AppDatabase
import app.schwindeljournal.data.local.entity.UserProfileEntity
import app.schwindeljournal.data.model.Modus
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Risikobasierte QA fuer neue Room-Persistenz (Arbeitsweise-Regel 2, CLAUDE.md):
 * prueft normalen Rundlauf UND adversariale Faelle (doppeltes Anlegen), die
 * echte Datenverlust-/Inkonsistenz-Risiken abbilden.
 */
@RunWith(AndroidJUnit4::class)
class UserProfileDaoTest {
    private lateinit var db: AppDatabase
    private lateinit var dao: UserProfileDao

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        dao = db.userProfileDao()
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun keinProfilVorAnlage() =
        runBlocking {
            assertNull(dao.getProfileOnce())
        }

    @Test
    fun profilAnlegenUndAuslesenErhaeltGewaehltenModus() =
        runBlocking {
            dao.insert(UserProfileEntity(modus = Modus.QUICK))

            val gespeichert = dao.getProfileOnce()
            assertEquals(Modus.QUICK, gespeichert?.modus)
        }

    @Test
    fun modusWechselWirdPersistiert() =
        runBlocking {
            val id = dao.insert(UserProfileEntity(modus = Modus.KOMPASS))

            dao.updateModus(id, Modus.PEER)

            assertEquals(Modus.PEER, dao.observeProfile().first()?.modus)
        }

    @Test
    fun doppelterOnboardingInsertErzeugtKeineZweiteZeile() =
        runBlocking {
            // Adversarialer Fall: Doppel-Tap auf "Los geht's" bei eingeschraenkter
            // Motorik, oder ein Retry nach einem Absturz mitten im ersten Insert.
            dao.insert(UserProfileEntity(modus = Modus.KOMPASS))
            dao.insert(UserProfileEntity(modus = Modus.QUICK))

            assertEquals(1, dao.countProfiles())
            // Der erste Insert gewinnt (IGNORE-Strategie), der zweite wird verworfen.
            assertEquals(Modus.KOMPASS, dao.getProfileOnce()?.modus)
        }

    @Test
    fun modusBleibtNachSchliessenUndNeuOeffnenDerDatenbankErhalten() =
        runBlocking {
            val context = ApplicationProvider.getApplicationContext<Context>()
            val dbName = "test_restart.db"
            context.deleteDatabase(dbName)
            var restartDb = Room.databaseBuilder(context, AppDatabase::class.java, dbName).build()
            restartDb.userProfileDao().insert(UserProfileEntity(modus = Modus.PEER))
            restartDb.close()

            // App-Prozess-Neustart simulieren: neue Room-Instanz auf dieselbe Datei.
            restartDb = Room.databaseBuilder(context, AppDatabase::class.java, dbName).build()
            val modusNachRestart = restartDb.userProfileDao().getProfileOnce()?.modus
            restartDb.close()
            context.deleteDatabase(dbName)

            assertEquals(Modus.PEER, modusNachRestart)
        }
}
