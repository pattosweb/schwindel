package app.schwindeljournal.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp

private const val FETT_MARKER = "**"
private const val ZEILENABSTAND_DP = 8

/**
 * Sehr kleiner, selbstgeschriebener Markdown-Renderer (Bordmittel statt externer
 * Bibliothek) - deckt genau das ab, was die Buchinhalte tatsaechlich nutzen:
 * "### "-Zwischenueberschriften, "- "-Aufzaehlungen und **fett**-Hervorhebungen.
 */
@Composable
fun MarkdownText(
    markdown: String,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        markdown.trim().split("\n").forEach { zeileRoh ->
            val zeile = zeileRoh.trim()
            when {
                zeile.isEmpty() -> Spacer(modifier = Modifier.height(ZEILENABSTAND_DP.dp))
                zeile.startsWith("### ") ->
                    Text(
                        text = zeile.removePrefix("### "),
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                    )
                zeile.startsWith("- ") ->
                    Row {
                        Text("• ")
                        Text(annotiereFettschrift(zeile.removePrefix("- ")))
                    }
                else -> Text(annotiereFettschrift(zeile))
            }
        }
    }
}

private fun annotiereFettschrift(text: String): AnnotatedString =
    buildAnnotatedString {
        var rest = text
        while (true) {
            val start = rest.indexOf(FETT_MARKER)
            if (start == -1) {
                append(rest)
                return@buildAnnotatedString
            }
            val ende = rest.indexOf(FETT_MARKER, start + FETT_MARKER.length)
            if (ende == -1) {
                append(rest)
                return@buildAnnotatedString
            }
            append(rest.substring(0, start))
            withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                append(rest.substring(start + FETT_MARKER.length, ende))
            }
            rest = rest.substring(ende + FETT_MARKER.length)
        }
    }
