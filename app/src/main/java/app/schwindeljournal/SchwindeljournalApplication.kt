package app.schwindeljournal

import android.app.Application
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import app.schwindeljournal.data.repository.ContentBlockRepository
import app.schwindeljournal.work.erstelleErinnerungsKanal
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltAndroidApp
class SchwindeljournalApplication :
    Application(),
    Configuration.Provider {
    @Inject
    lateinit var contentBlockRepository: ContentBlockRepository

    @Inject
    lateinit var hiltWorkerFactory: HiltWorkerFactory

    private val anwendungsScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder().setWorkerFactory(hiltWorkerFactory).build()

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        erstelleErinnerungsKanal(this)
        // Wissens-Bibliothek-Inhalte bei jedem Start neu einspielen (REPLACE-Seed,
        // siehe ContentBlockEntity-Doku) - Netzwerk-/UI-unabhaengig, daher hier statt
        // WorkManager (das ist fuer wiederkehrende/geplante Hintergrundarbeit da,
        // siehe ReminderScheduler, nicht fuer einen kurzen Start-Vorgang).
        anwendungsScope.launch { contentBlockRepository.seedInhalte() }
    }
}
