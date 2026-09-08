package app.schwindeljournal.ui.components

import androidx.compose.foundation.layout.heightIn
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneOffset

/** Antippbarer Datumsauswahl-Button + Material3-DatePickerDialog (Bordmittel). */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatumAuswahl(
    label: String,
    ausgewaehltesDatum: LocalDate?,
    onDatumGewaehlt: (LocalDate?) -> Unit,
) {
    var dialogOffen by remember { mutableStateOf(false) }

    OutlinedButton(onClick = { dialogOffen = true }, modifier = Modifier.heightIn(min = 48.dp)) {
        Text(if (ausgewaehltesDatum != null) "$label: ${formatiereDatum(ausgewaehltesDatum)}" else "$label auswählen")
    }

    if (dialogOffen) {
        val zustand =
            rememberDatePickerState(
                initialSelectedDateMillis =
                    ausgewaehltesDatum
                        ?.atStartOfDay(ZoneOffset.UTC)
                        ?.toInstant()
                        ?.toEpochMilli(),
            )
        DatePickerDialog(
            onDismissRequest = { dialogOffen = false },
            confirmButton = {
                TextButton(onClick = {
                    val millis = zustand.selectedDateMillis
                    if (millis != null) {
                        onDatumGewaehlt(Instant.ofEpochMilli(millis).atZone(ZoneOffset.UTC).toLocalDate())
                    }
                    dialogOffen = false
                }) { Text("Übernehmen") }
            },
            dismissButton = {
                TextButton(onClick = { dialogOffen = false }) { Text("Abbrechen") }
            },
        ) { DatePicker(state = zustand) }
    }
}
