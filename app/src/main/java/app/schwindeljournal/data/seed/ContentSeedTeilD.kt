package app.schwindeljournal.data.seed

import app.schwindeljournal.data.local.entity.ContentBlockEntity
import app.schwindeljournal.data.model.BuchTeil
import app.schwindeljournal.data.model.Modus
import app.schwindeljournal.data.model.VariantenTiefe

internal val teilDBloecke: List<ContentBlockEntity> =
    listOf(
        ContentBlockEntity(
            id = "teilD-beispiele",
            buchTeil = BuchTeil.D,
            titel = "Schwacher vs. starker Journal-Eintrag",
            variantenTiefe = VariantenTiefe.VOLL,
            sichtbarInModus = setOf(Modus.KOMPASS),
            inhaltMarkdown =
                """
                Die meisten Menschen, die zum ersten Mal wegen Schwindel beim Arzt sitzen, beschreiben ihn ungefähr so: "Mir wurde schwindelig, das kam öfter vor, mal schlimmer, mal weniger." Das ist ehrlich – und für eine Einordnung fast wertlos. Nicht, weil die Beschreibung falsch wäre, sondern weil ihr die Details fehlen, die einen Unterschied machen.

                ### Beispiele
                - Schwach: "Vormittags schwindelig gefühlt" – Stark: "10:15 Uhr, beim Aufstehen vom Schreibtisch nach 2 Std. Bildschirmarbeit, Drehschwindel ca. 20 Sek., danach Nackenverspannung rechts, keine Übelkeit"
                - Schwach: "War ein schlechter Tag" – Stark: "Schwindel 3x aufgetreten, Nacht davor nur 5 Std. Schlaf auf neuem, flacherem Kissen"

                Der Unterschied liegt nicht in der Textmenge, sondern in vier Dingen: **Zeitpunkt, Auslöser-Situation, exakte Dauer, Begleitumstände.** Das sind genau die Spalten deines Journals – sie existieren nicht, um Bürokratie zu erzeugen, sondern weil jede von ihnen eine andere mögliche Ursache ein- oder ausschließen hilft.

                ### Konsequent bleiben – auch an "nichts passiert"-Tagen
                Ein häufiger Fehler: Nur die Tage mit Schwindel werden dokumentiert, ruhige Tage bleiben leer. Damit geht die wichtigste Information verloren – der Kontrast. Trage daher an jedem Tag zumindest die Tagesbewertung ein, auch wenn nichts war. Ein Muster wie "immer schwindelig nach kurzen Nächten" lässt sich nur erkennen, wenn du auch die Nächte mit gutem Schlaf dokumentierst.
                """.trimIndent(),
        ),
        ContentBlockEntity(
            id = "teilD-beispiele-peer",
            buchTeil = BuchTeil.D,
            titel = "Schwacher vs. starker Journal-Eintrag",
            variantenTiefe = VariantenTiefe.PEER,
            sichtbarInModus = setOf(Modus.PEER),
            inhaltMarkdown =
                """
                Am Anfang habe ich auch nur geschrieben "war ein schlechter Tag". Das bringt fast nichts. Erst als ich angefangen habe, Uhrzeit, genaue Situation, Dauer und Begleitumstände festzuhalten, hat sich für mich ein Muster gezeigt. Kleiner Aufwand, großer Unterschied.
                """.trimIndent(),
        ),
        ContentBlockEntity(
            id = "teilD-checkliste",
            buchTeil = BuchTeil.D,
            titel = "Was Patient und Arzt tatsächlich brauchen",
            variantenTiefe = VariantenTiefe.VOLL,
            sichtbarInModus = setOf(Modus.KOMPASS),
            inhaltMarkdown =
                """
                Was für die behandelnde Fachperson besonders wertvoll ist – mehr als jede Einzelbeschreibung:

                - Das **Muster über Zeit**, nicht der einzelne Vorfall
                - Die **exakte Dauer** einer Episode: Sekunden, Minuten oder Stunden sind diagnostisch sehr unterschiedlich zu bewerten
                - Eine schnelle Bestätigung, dass **keines der Warnzeichen** aus Teil A aufgetreten ist – das beschleunigt die erste Einschätzung erheblich
                - Ob der Schwindel durch Kopf-/Körperposition auslösbar ist oder auch in Ruhe auftritt
                - Die **Steckbrief-Seite** – Medikamente und Vorerkrankungen sind damit bereits vorab dokumentiert, statt im Gespräch erst erfragt werden zu müssen
                """.trimIndent(),
        ),
        ContentBlockEntity(
            id = "teilD-checkliste-peer",
            buchTeil = BuchTeil.D,
            titel = "Was Patient und Arzt tatsächlich brauchen",
            variantenTiefe = VariantenTiefe.PEER,
            sichtbarInModus = setOf(Modus.PEER),
            inhaltMarkdown =
                """
                Aus eigener Erfahrung: Was meinem Arzt am meisten geholfen hat, war nicht die Beschreibung eines einzelnen Vorfalls, sondern das Muster über mehrere Wochen – plus die klare Ansage, dass keines der Warnzeichen aufgetreten war. Das hat das Gespräch enorm beschleunigt.
                """.trimIndent(),
        ),
    )
