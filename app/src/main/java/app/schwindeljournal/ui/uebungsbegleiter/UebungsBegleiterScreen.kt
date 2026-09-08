package app.schwindeljournal.ui.uebungsbegleiter

import androidx.compose.runtime.Composable
import app.schwindeljournal.data.model.Modus
import app.schwindeljournal.ui.components.PlatzhalterScreen

@Composable
fun UebungsBegleiterScreen(modus: Modus?) {
    PlatzhalterScreen(titel = "Übungs-Begleiter", modus = modus)
}
