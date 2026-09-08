package app.schwindeljournal

import android.app.Application
import app.schwindeljournal.data.repository.ContentBlockRepository
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltAndroidApp
class SchwindeljournalApplication : Application() {
    @Inject
    lateinit var contentBlockRepository: ContentBlockRepository

    private val anwendungsScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        // Wissens-Bibliothek-Inhalte bei jedem Start neu einspielen (REPLACE-Seed,
        // siehe ContentBlockEntity-Doku) - Netzwerk-/UI-unabhaengig, daher hier statt
        // WorkManager (Engineering-Standard: WorkManager fuer wiederkehrende/geplante
        // Hintergrundarbeit, nicht fuer einen kurzen Start-Vorgang).
        anwendungsScope.launch { contentBlockRepository.seedInhalte() }
    }
}
