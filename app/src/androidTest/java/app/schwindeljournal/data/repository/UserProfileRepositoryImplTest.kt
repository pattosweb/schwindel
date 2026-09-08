package app.schwindeljournal.data.repository

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import app.schwindeljournal.data.local.AppDatabase
import app.schwindeljournal.data.model.Modus
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class UserProfileRepositoryImplTest {
    private lateinit var db: AppDatabase
    private lateinit var repository: UserProfileRepository

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        repository = UserProfileRepositoryImpl(db.userProfileDao())
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun updateModusOhneVorhandenesProfilSchlaegtKontrolliertFehlUndCrashtNicht() =
        runBlocking {
            val ergebnis = repository.updateModus(Modus.PEER)

            assertTrue(ergebnis.isFailure)
        }

    @Test
    fun modusWechselNachProfilAnlageIstUeberProfileFlowSichtbar() =
        runBlocking {
            repository.createProfile(Modus.KOMPASS)

            repository.updateModus(Modus.QUICK)

            assertEquals(Modus.QUICK, repository.profile.first()?.modus)
        }
}
