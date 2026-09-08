package app.schwindeljournal.ui.components

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.speech.RecognizerIntent
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import timber.log.Timber
import java.util.Locale

/**
 * Freitextfeld mit gleichwertiger Spracheingabe-Alternative (UX-Leitplanke, CLAUDE.md:
 * "Spracheingabe als gleichwertige Alternative zu jedem Freitextfeld", nicht
 * verhandelbar). Nutzt den System-Diktier-Intent (Bordmittel, kein eigenes
 * SpeechRecognizer-Lifecycle-/Berechtigungs-Handling noetig – "Erst Bordmittel, dann
 * Eigenbau").
 */
@Composable
fun SprachEingabeTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    minLines: Int = 1,
) {
    val context = LocalContext.current
    val launcher =
        rememberLauncherForActivityResult(
            ActivityResultContracts.StartActivityForResult(),
        ) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val erkannterText =
                    result.data
                        ?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                        ?.firstOrNull()
                if (!erkannterText.isNullOrBlank()) {
                    onValueChange(if (value.isBlank()) erkannterText else "$value $erkannterText")
                }
            }
        }

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        minLines = minLines,
        modifier = modifier.fillMaxWidth(),
        trailingIcon = {
            IconButton(
                modifier = Modifier.size(48.dp),
                onClick = {
                    val intent =
                        Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
                            putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
                            putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.GERMANY.toLanguageTag())
                            putExtra(RecognizerIntent.EXTRA_PROMPT, label)
                        }
                    try {
                        launcher.launch(intent)
                    } catch (e: ActivityNotFoundException) {
                        Timber.w(e, "Spracherkennung auf diesem Geraet nicht verfuegbar")
                        Toast
                            .makeText(
                                context,
                                "Spracheingabe auf diesem Gerät nicht verfügbar – bitte eintippen.",
                                Toast.LENGTH_SHORT,
                            ).show()
                    }
                },
            ) {
                Icon(imageVector = Icons.Filled.Mic, contentDescription = "Spracheingabe für $label")
            }
        },
    )
}
