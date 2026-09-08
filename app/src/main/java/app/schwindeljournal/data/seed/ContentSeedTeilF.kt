package app.schwindeljournal.data.seed

import app.schwindeljournal.data.local.entity.ContentBlockEntity
import app.schwindeljournal.data.model.BuchTeil
import app.schwindeljournal.data.model.Modus
import app.schwindeljournal.data.model.VariantenTiefe

internal val teilFBloecke: List<ContentBlockEntity> =
    listOf(
        ContentBlockEntity(
            id = "teilF-tabelle",
            buchTeil = BuchTeil.F,
            titel = "Beobachtung → möglicher Ansprechpartner",
            variantenTiefe = VariantenTiefe.VOLL,
            sichtbarInModus = setOf(Modus.KOMPASS),
            inhaltMarkdown =
                """
                Unabhängig davon, was du bei dir beobachtest: Der Hausarzt/die Hausärztin ist fast immer der richtige erste Ansprechpartner. Von dort erfolgt bei Bedarf die Überweisung an die passende Fachrichtung. Diese Tabelle ersetzt nicht das Gespräch mit deinem Hausarzt – sie hilft dir, deine eigenen Beobachtungen in Worte zu fassen und gezielter nachzufragen.

                - Drehschwindel, Ohrensausen, Hörveränderung → HNO-Arzt/-Ärztin
                - Nackenschmerz, Verspannung, Schwindel bei Kopfbewegung → Physiotherapeut/-in, Chiropraktiker/-in, Orthopäde/Orthopädin
                - Kopfschmerz-Muster, neurologische Begleitsymptome → Neurologe/Neurologin
                - Herzklopfen, Brustschmerz, Schwindel bei Belastung → Kardiologe/Kardiologin
                - Schwindel im Zusammenhang mit neuen Medikamenten → Hausarzt/Hausärztin (Medikationsprüfung)
                - Schwindel eng mit Stress/Angst verknüpft → Hausarzt/Hausärztin, ggf. psychotherapeutische Unterstützung
                """.trimIndent(),
        ),
        ContentBlockEntity(
            id = "teilF-tabelle-peer",
            buchTeil = BuchTeil.F,
            titel = "Beobachtung → möglicher Ansprechpartner",
            variantenTiefe = VariantenTiefe.PEER,
            sichtbarInModus = setOf(Modus.PEER),
            inhaltMarkdown =
                """
                Der Hausarzt ist bei mir immer der erste Weg gewesen. Von dort ging's je nach Beobachtung weiter – HNO, Physio/Chiro, oder Neurologie. Die Tabelle im Buch hilft dir, deine eigene Beobachtung in die richtige Richtung zu übersetzen.
                """.trimIndent(),
        ),
        ContentBlockEntity(
            id = "teilF-mitnehmen",
            buchTeil = BuchTeil.F,
            titel = "Was du zum Termin mitnimmst",
            variantenTiefe = VariantenTiefe.VOLL,
            sichtbarInModus = setOf(Modus.KOMPASS),
            inhaltMarkdown =
                """
                ### Was du zum Termin mitnimmst
                - Deine ausgefüllte Steckbrief-Seite
                - Dein Journal (oder die Abschlussauswertung, wenn die 60 Tage bereits um sind)
                - Falls vorhanden: den PDF-Export aus der Schwindeljournal-App

                ### Was du dort ansprechen kannst
                - "Ich habe über X Tage/Wochen beobachtet, dass..." (konkretes Muster benennen)
                - "Könnte meine Halswirbelsäule eine Rolle spielen? Ich habe dazu diese Beobachtungen gemacht..." (Teil B als Gesprächsanstoß, nicht als Diagnose)
                - "Welche der Ursachen aus meiner Übersicht (Teil C) kommen für mich infrage, und wie grenzen wir das ein?"
                """.trimIndent(),
        ),
        ContentBlockEntity(
            id = "teilF-mitnehmen-peer",
            buchTeil = BuchTeil.F,
            titel = "Was du zum Termin mitnimmst",
            variantenTiefe = VariantenTiefe.PEER,
            sichtbarInModus = setOf(Modus.PEER),
            inhaltMarkdown =
                """
                Ich hab gelernt: Steckbrief, Journal (oder die Auswertung) und wenn vorhanden der App-Export – das nehme ich seitdem zu jedem Termin mit. Spart Zeit und ich vergesse nichts Wichtiges.
                """.trimIndent(),
        ),
    )
