package app.schwindeljournal.ui.wissensbibliothek

import androidx.compose.runtime.Composable
import app.schwindeljournal.data.model.Modus
import app.schwindeljournal.ui.components.PlatzhalterScreen

@Composable
fun WissensBibliothekScreen(modus: Modus?) {
    PlatzhalterScreen(titel = "Wissens-Bibliothek", modus = modus)
}
