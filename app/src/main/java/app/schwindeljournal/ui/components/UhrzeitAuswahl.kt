package app.schwindeljournal.ui.components

import androidx.compose.foundation.layout.heightIn
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.time.LocalTime

/** Antippbarer Uhrzeitauswahl-Button + Material3-TimePicker-Dialog (Bordmittel). */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UhrzeitAuswahl(
    label: String,
    ausgewaehlteUhrzeit: LocalTime,
    onUhrzeitGewaehlt: (LocalTime) -> Unit,
) {
    var dialogOffen by remember { mutableStateOf(false) }

    OutlinedButton(onClick = { dialogOffen = true }, modifier = Modifier.heightIn(min = 48.dp)) {
        Text("$label: ${formatiereUhrzeit(ausgewaehlteUhrzeit)}")
    }

    if (dialogOffen) {
        val zustand =
            rememberTimePickerState(
                initialHour = ausgewaehlteUhrzeit.hour,
                initialMinute = ausgewaehlteUhrzeit.minute,
                is24Hour = true,
            )
        AlertDialog(
            onDismissRequest = { dialogOffen = false },
            confirmButton = {
                TextButton(onClick = {
                    onUhrzeitGewaehlt(LocalTime.of(zustand.hour, zustand.minute))
                    dialogOffen = false
                }) { Text("Übernehmen") }
            },
            dismissButton = {
                TextButton(onClick = { dialogOffen = false }) { Text("Abbrechen") }
            },
            text = { TimePicker(state = zustand) },
        )
    }
}
