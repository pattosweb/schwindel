package app.schwindeljournal.data.seed

import app.schwindeljournal.data.model.BuchTeil
import app.schwindeljournal.data.model.Sprache
import app.schwindeljournal.data.model.verfuegbareSprachen
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * Adversariale QA fuer den Content-Seed (CLAUDE.md Regel 2), gezielt gegen die Bug-
 * Klasse, die schon einmal real aufgetreten ist (siehe PROJECT_LOG.md): Zeilen mit
 * demselben Room-Primary-Key (id) ueberschreiben sich beim REPLACE-Seed gegenseitig
 * still, ohne dass Build oder App das anzeigen. Reines JVM-Unit-Test (kein Android/
 * Room noetig), laeuft daher schnell in jedem Build - vorher gab es noch keinen
 * einzigen Unit-Test in diesem Projekt (nur androidTest).
 */
class ContentSeedTest {
    @Test
    fun `keine doppelten ids ueber alle Sprachen und Varianten`() {
        val ids = ContentSeed.alleBloecke.map { it.id }
        val duplikate = ids.groupingBy { it }.eachCount().filter { it.value > 1 }
        assertTrue("Doppelte ContentBlock-ids gefunden: $duplikate", duplikate.isEmpty())
    }

    @Test
    fun `jede verfuegbare Sprache hat genau einen nicht-leeren Warnzeichen-Block`() {
        verfuegbareSprachen.forEach { sprache ->
            val warnzeichenBloecke =
                ContentSeed.alleBloecke.filter { it.sprache == sprache && it.istWarnzeichenInhalt }
            assertEquals(
                "Sprache $sprache sollte genau einen Warnzeichen-Block haben",
                1,
                warnzeichenBloecke.size,
            )
            assertTrue(
                "Warnzeichen-Text fuer $sprache ist leer/blank",
                warnzeichenBloecke.single().inhaltMarkdown.isNotBlank(),
            )
        }
    }

    @Test
    fun `jede verfuegbare Sprache deckt alle Buchteile A bis H ab`() {
        verfuegbareSprachen.forEach { sprache ->
            val abgedeckteTeile =
                ContentSeed.alleBloecke
                    .filter { it.sprache == sprache }
                    .map { it.buchTeil }
                    .toSet()
            BuchTeil.entries.forEach { teil ->
                assertTrue("Sprache $sprache fehlt Buchteil $teil", teil in abgedeckteTeile)
            }
        }
    }

    @Test
    fun `Deutsch und Englisch haben die gleiche Zeilenanzahl`() {
        val anzahlDe = ContentSeed.alleBloecke.count { it.sprache == Sprache.DE }
        val anzahlEn = ContentSeed.alleBloecke.count { it.sprache == Sprache.EN }
        assertEquals("DE- und EN-Content sollten inhaltlich deckungsgleich sein", anzahlDe, anzahlEn)
    }
}
