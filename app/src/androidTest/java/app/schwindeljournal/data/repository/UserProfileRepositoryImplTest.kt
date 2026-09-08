package app.schwindeljournal.data.repository

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import app.schwindeljournal.data.local.AppDatabase
import app.schwindeljournal.data.local.entity.AnsprechpartnerEntity
import app.schwindeljournal.data.local.entity.MedikamentEntity
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
        repository = UserProfileRepositoryImpl(db.userProfileDao(), db.medikamentDao(), db.ansprechpartnerDao())
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

    @Test
    fun steckbriefUpdateWirdPersistiert() =
        runBlocking {
            repository.createProfile(Modus.KOMPASS)
            val profil = repository.profile.first()!!

            repository.updateProfile(profil.copy(geburtsjahr = 1990, vorerkrankungen = "Migräne"))

            val aktualisiert = repository.profile.first()!!
            assertEquals(1990, aktualisiert.geburtsjahr)
            assertEquals("Migräne", aktualisiert.vorerkrankungen)
        }

    @Test
    fun medikamentHinzufuegenUndLoeschenAendertListe() =
        runBlocking {
            repository.createProfile(Modus.KOMPASS)
            val profilId = repository.profile.first()!!.id

            val id = repository.addMedikament(MedikamentEntity(profileId = profilId, name = "Ibuprofen")).getOrThrow()
            assertEquals(1, repository.observeMedikamente(profilId).first().size)

            repository.deleteMedikament(MedikamentEntity(id = id, profileId = profilId, name = "Ibuprofen"))
            assertTrue(repository.observeMedikamente(profilId).first().isEmpty())
        }

    @Test
    fun ansprechpartnerHinzufuegenUndLoeschenAendertListe() =
        runBlocking {
            repository.createProfile(Modus.KOMPASS)
            val profilId = repository.profile.first()!!.id

            val id =
                repository
                    .addAnsprechpartner(AnsprechpartnerEntity(profileId = profilId, rolle = "Hausarzt"))
                    .getOrThrow()
            assertEquals(1, repository.observeAnsprechpartner(profilId).first().size)

            repository.deleteAnsprechpartner(AnsprechpartnerEntity(id = id, profileId = profilId, rolle = "Hausarzt"))
            assertTrue(repository.observeAnsprechpartner(profilId).first().isEmpty())
        }
}
