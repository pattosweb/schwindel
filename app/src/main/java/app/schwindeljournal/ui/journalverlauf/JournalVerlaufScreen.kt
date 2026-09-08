package app.schwindeljournal.ui.journalverlauf

import androidx.compose.runtime.Composable
import app.schwindeljournal.data.model.Modus
import app.schwindeljournal.ui.components.PlatzhalterScreen

@Composable
fun JournalVerlaufScreen(modus: Modus?) {
    PlatzhalterScreen(titel = "Journal-Verlauf", modus = modus)
}
