package app.schwindeljournal.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import app.schwindeljournal.data.model.SymptomTyp
import app.schwindeljournal.data.model.anzeigename

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun BegleitsymptomeAuswahl(
    ausgewaehlt: Set<SymptomTyp>,
    sonstigesFreitext: String,
    onToggle: (SymptomTyp) -> Unit,
    onSonstigesFreitextChange: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            SymptomTyp.entries.forEach { typ ->
                FilterChip(
                    selected = typ in ausgewaehlt,
                    onClick = { onToggle(typ) },
                    label = { Text(typ.anzeigename()) },
                    modifier = Modifier.heightIn(min = 48.dp),
                )
            }
        }
        if (SymptomTyp.SONSTIGES in ausgewaehlt) {
            Spacer(modifier = Modifier.height(8.dp))
            SprachEingabeTextField(
                value = sonstigesFreitext,
                onValueChange = onSonstigesFreitextChange,
                label = "Sonstiges Symptom",
            )
        }
    }
}
