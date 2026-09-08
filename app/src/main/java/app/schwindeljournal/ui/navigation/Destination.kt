package app.schwindeljournal.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.SelfImprovement
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Destination(
    val route: String,
) {
    data object Onboarding : Destination("onboarding")

    data object SchnellErfassung : Destination("schnell_erfassung")

    data object JournalVerlauf : Destination("journal_verlauf")

    data object WissensBibliothek : Destination("wissens_bibliothek")

    data object UebungsBegleiter : Destination("uebungs_begleiter")

    data object Einstellungen : Destination("einstellungen")
}

data class BottomNavItem(
    val destination: Destination,
    val label: String,
    val icon: ImageVector,
)

/**
 * Zentrale Liste der Haupt-Navigationsziele (Roadmap Phase 0). Bewusst eine
 * einzige Quelle statt verstreuter Bottom-Nav-Definitionen pro Screen.
 */
val bottomNavItems =
    listOf(
        BottomNavItem(Destination.SchnellErfassung, "Erfassen", Icons.Filled.Bolt),
        BottomNavItem(Destination.JournalVerlauf, "Verlauf", Icons.Filled.History),
        BottomNavItem(Destination.WissensBibliothek, "Wissen", Icons.AutoMirrored.Filled.MenuBook),
        BottomNavItem(Destination.UebungsBegleiter, "Übungen", Icons.Filled.SelfImprovement),
        BottomNavItem(Destination.Einstellungen, "Einstellungen", Icons.Filled.Settings),
    )
