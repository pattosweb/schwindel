package app.schwindeljournal.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColors =
    lightColorScheme(
        primary = SchwindelBlauLight,
    )

private val DarkColors =
    darkColorScheme(
        primary = SchwindelBlauDark,
    )

/**
 * Bewusst ohne Motion-/Farbverlaufs-Spielereien (siehe CLAUDE.md, "Besondere
 * UX-Leitplanken") – nur Standard-Material3-Farbwechsel hell/dunkel.
 */
@Composable
fun SchwindeljournalTheme(content: @Composable () -> Unit) {
    val colors = if (isSystemInDarkTheme()) DarkColors else LightColors
    MaterialTheme(
        colorScheme = colors,
        typography = Typography,
        content = content,
    )
}
