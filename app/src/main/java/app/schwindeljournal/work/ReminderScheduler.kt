package app.schwindeljournal.work

import android.content.Context
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import java.time.Duration
import java.time.LocalDateTime
import java.time.LocalTime
import java.util.concurrent.TimeUnit

/**
 * Zentraler Einstiegspunkt fuer die taegliche Erinnerung (CLAUDE.md Engineering-
 * Standard "Zentraler Scheduler": ausschliesslich ueber WorkManager, keine verstreuten
 * AlarmManager-Aufrufe). WorkManager kennt keine "taeglich um exakt HH:mm"-Periodizitaet
 * direkt - stattdessen wird pro Tag ein einzelner OneTimeWorkRequest mit passendem
 * initialDelay geplant, und der Worker selbst plant am Ende seinen Nachfolger fuer den
 * naechsten Tag (siehe ReminderWorker).
 */
object ReminderScheduler {
    const val UNIQUE_WORK_NAME = "taegliche_erinnerung"

    fun planeFuer(
        context: Context,
        zielUhrzeit: LocalTime,
    ) {
        val verzoegerung = verzoegerungBisNaechstem(zielUhrzeit)
        plane(context, verzoegerung)
    }

    fun planeInEinemTag(context: Context) {
        plane(context, Duration.ofDays(1))
    }

    fun abbrechen(context: Context) {
        WorkManager.getInstance(context).cancelUniqueWork(UNIQUE_WORK_NAME)
    }

    private fun plane(
        context: Context,
        verzoegerung: Duration,
    ) {
        val anfrage =
            OneTimeWorkRequestBuilder<ReminderWorker>()
                .setInitialDelay(verzoegerung.toMillis(), TimeUnit.MILLISECONDS)
                .build()
        WorkManager
            .getInstance(context)
            .enqueueUniqueWork(UNIQUE_WORK_NAME, ExistingWorkPolicy.REPLACE, anfrage)
    }

    private fun verzoegerungBisNaechstem(zielUhrzeit: LocalTime): Duration {
        val jetzt = LocalDateTime.now()
        var ziel = LocalDateTime.of(jetzt.toLocalDate(), zielUhrzeit)
        if (!ziel.isAfter(jetzt)) {
            ziel = ziel.plusDays(1)
        }
        return Duration.between(jetzt, ziel)
    }
}
