package app.schwindeljournal.work

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import app.schwindeljournal.MainActivity
import app.schwindeljournal.R

const val ERINNERUNG_KANAL_ID = "taegliche_erinnerung"
private const val ERINNERUNG_NOTIFICATION_ID = 1001

/** Einmalig beim App-Start angelegt (siehe SchwindeljournalApplication). */
fun erstelleErinnerungsKanal(context: Context) {
    val kanal =
        NotificationChannel(
            ERINNERUNG_KANAL_ID,
            "Tägliche Journal-Erinnerung",
            NotificationManager.IMPORTANCE_DEFAULT,
        ).apply {
            description = "Erinnert dich, deinen Schwindeljournal-Eintrag für heute nachzutragen."
        }
    context.getSystemService(NotificationManager::class.java)?.createNotificationChannel(kanal)
}

/** Zeigt die Erinnerung nur, wenn die Berechtigung vorliegt (kein Crash auf API < 33 ohne Check-Notwendigkeit). */
fun zeigeErinnerungsBenachrichtigung(context: Context) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
        ActivityCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) !=
        PackageManager.PERMISSION_GRANTED
    ) {
        return
    }

    val oeffneAppIntent =
        Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
    val pendingIntent =
        PendingIntent.getActivity(
            context,
            0,
            oeffneAppIntent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT,
        )

    val benachrichtigung =
        NotificationCompat
            .Builder(context, ERINNERUNG_KANAL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Schwindeljournal")
            .setContentText("Zeit für deinen heutigen Eintrag – dauert nur wenige Sekunden.")
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()

    NotificationManagerCompat.from(context).notify(ERINNERUNG_NOTIFICATION_ID, benachrichtigung)
}
