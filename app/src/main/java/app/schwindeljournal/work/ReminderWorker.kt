package app.schwindeljournal.work

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import app.schwindeljournal.data.repository.JournalEntryRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.first
import timber.log.Timber
import java.time.LocalDate

/**
 * Zeigt die taegliche Erinnerung nur, wenn heute noch kein Eintrag existiert (kein
 * Nag-Screen, wenn die Person bereits erfasst hat - Betroffenen-Sicht: nicht
 * bevormunden). Plant sich am Ende selbst fuer den naechsten Tag neu (siehe
 * ReminderScheduler-Doku).
 */
@HiltWorker
class ReminderWorker
    @AssistedInject
    constructor(
        @Assisted context: Context,
        @Assisted params: WorkerParameters,
        private val journalRepository: JournalEntryRepository,
    ) : CoroutineWorker(context, params) {
        // Absichtlich breit gefangen: das ist die aeusserste Grenze eines Hintergrund-
        // Workers - jede unerwartete Exception (Room, Notification-API, ...) soll
        // geloggt und die naechste Erinnerung trotzdem geplant werden, statt den Worker
        // dauerhaft stillzulegen.
        @Suppress("TooGenericExceptionCaught")
        override suspend fun doWork(): Result =
            try {
                val heuteBereitsErfasst = journalRepository.alleEintraege.first().any { it.datum == LocalDate.now() }
                if (!heuteBereitsErfasst) {
                    zeigeErinnerungsBenachrichtigung(applicationContext)
                }
                ReminderScheduler.planeInEinemTag(applicationContext)
                Result.success()
            } catch (e: Exception) {
                Timber.e(e, "Fehler im Erinnerungs-Worker")
                // Trotzdem fuer morgen neu planen, damit ein einzelner Fehler die
                // Erinnerung nicht dauerhaft stilllegt.
                ReminderScheduler.planeInEinemTag(applicationContext)
                Result.failure()
            }
    }
