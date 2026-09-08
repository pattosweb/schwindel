package app.schwindeljournal.ui.schnellerfassung

import androidx.compose.runtime.Composable
import app.schwindeljournal.data.model.Modus
import app.schwindeljournal.ui.components.PlatzhalterScreen

@Composable
fun SchnellErfassungScreen(modus: Modus?) {
    PlatzhalterScreen(titel = "Schnell-Erfassung", modus = modus)
}
