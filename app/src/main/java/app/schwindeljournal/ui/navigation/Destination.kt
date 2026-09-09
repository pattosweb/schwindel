package app.schwindeljournal.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.SelfImprovement
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import app.schwindeljournal.data.model.Sprache
import app.schwindeljournal.ui.shared.LocalSprache

sealed class Destination(
    val route: String,
) {
    data object Onboarding : Destination("onboarding")

    data object SchnellErfassung : Destination("schnell_erfassung")

    data object Steckbrief : Destination("steckbrief")

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
 * einzige Quelle statt verstreuter Bottom-Nav-Definitionen pro Screen. @Composable
 * (statt einfachem val), damit die Labels der aktuellen [LocalSprache] folgen.
 */
@Composable
fun bottomNavItems(): List<BottomNavItem> {
    val texte = bottomNavTexte(LocalSprache.current)
    return listOf(
        BottomNavItem(Destination.SchnellErfassung, texte.erfassen, Icons.Filled.Bolt),
        BottomNavItem(Destination.JournalVerlauf, texte.verlauf, Icons.Filled.History),
        BottomNavItem(Destination.WissensBibliothek, texte.wissen, Icons.AutoMirrored.Filled.MenuBook),
        BottomNavItem(Destination.UebungsBegleiter, texte.uebungen, Icons.Filled.SelfImprovement),
        BottomNavItem(Destination.Einstellungen, texte.einstellungen, Icons.Filled.Settings),
    )
}

private data class BottomNavTexte(
    val erfassen: String,
    val verlauf: String,
    val wissen: String,
    val uebungen: String,
    val einstellungen: String,
)

private fun bottomNavTexte(sprache: Sprache): BottomNavTexte =
    when (sprache) {
        Sprache.EN -> BottomNavTexte("Log", "History", "Learn", "Exercises", "Settings")
        else -> BottomNavTexte("Erfassen", "Verlauf", "Wissen", "Übungen", "Einstellungen")
    }
