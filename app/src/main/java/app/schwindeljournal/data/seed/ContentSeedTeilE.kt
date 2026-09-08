package app.schwindeljournal.data.seed

import app.schwindeljournal.data.local.entity.ContentBlockEntity
import app.schwindeljournal.data.model.BuchTeil
import app.schwindeljournal.data.model.Modus
import app.schwindeljournal.data.model.VariantenTiefe

// id ist Room-Primary-Key - VOLL/PEER brauchen unterschiedliche ids (Suffix -peer),
// siehe ContentSeedTeilC.kt-Kommentar (adversarial gefundener Bug).
private fun uebung(
    id: String,
    titel: String,
    voll: String,
    peer: String,
): List<ContentBlockEntity> =
    listOf(
        ContentBlockEntity(
            id = id,
            buchTeil = BuchTeil.E,
            titel = titel,
            variantenTiefe = VariantenTiefe.VOLL,
            sichtbarInModus = setOf(Modus.KOMPASS),
            inhaltMarkdown = voll.trimIndent(),
        ),
        ContentBlockEntity(
            id = "$id-peer",
            buchTeil = BuchTeil.E,
            titel = titel,
            variantenTiefe = VariantenTiefe.PEER,
            sichtbarInModus = setOf(Modus.PEER),
            inhaltMarkdown = peer.trimIndent(),
        ),
    )

private const val TEIL_E_INTRO =
    "Alles in diesem Kapitel sind sanfte, allgemein bekannte Selbsthilfe-Ansätze – keine " +
        "Behandlung im medizinischen Sinne. Wenn eine akute oder unklare Schwindelursache " +
        "vorliegt, sprich vor dem Ausprobieren mit deinem Arzt oder deiner Physiotherapeutin, " +
        "ob und welche der folgenden Übungen für dich geeignet sind."

internal val teilEBloecke: List<ContentBlockEntity> =
    uebung(
        id = "teilE-uebung-nackenmobilisation",
        titel = "Sanfte Nackenmobilisation",
        voll =
            """
            $TEIL_E_INTRO

            Langsame, kleine Bewegungen – Kopf leicht nach links/rechts drehen, sanft zur Seite neigen, jeweils nur so weit, wie es sich angenehm anfühlt, einige Atemzüge halten. Ziel ist Beweglichkeit und Entspannung der Nackenmuskulatur, nicht das "Knacken" oder Einrenken von Wirbeln – das gehört ausschließlich in die Hände einer ausgebildeten Fachperson.
            """,
        peer =
            """
            Kleine, sanfte Bewegungen – Kopf leicht drehen, sanft zur Seite neigen, nur so weit, wie es angenehm ist. Kein Knacken, kein Einrenken – das ist Sache einer Fachperson, nicht deiner eigenen Übung.
            """,
    ) +
        uebung(
            id = "teilE-uebung-cawthorne-cooksey",
            titel = "Vestibuläres Training nach Cawthorne-Cooksey",
            voll =
                """
                Ein seit Jahrzehnten bekanntes, undogmatisches Übungskonzept: kontrollierte Augenbewegungen (z. B. einem Finger mit den Augen folgen, Kopf dabei stillhalten), anschließend kontrollierte Kopfbewegungen bei fixiertem Blick, später Kombinationen aus Kopf- und Körperbewegung im Sitzen und Stehen. Die Grundidee: Das Gleichgewichtssystem "lernt" durch wiederholten, kontrollierten Reiz, mit widersprüchlichen Signalen besser umzugehen. Baue diese Übungen langsam auf und steigere nur, wenn sie gut vertragen werden.
                """,
            peer =
                """
                Dieses Übungskonzept gibt es schon seit Jahrzehnten: erst mit den Augen einem Finger folgen, Kopf stillhalten, dann Kopfbewegungen bei fixiertem Blick, später Kombinationen im Sitzen und Stehen. Die Idee dahinter: dein Gleichgewichtssystem lernt durch wiederholten, kontrollierten Reiz. Langsam steigern, nicht übertreiben.
                """,
        ) +
        uebung(
            id = "teilE-uebung-atemuebung",
            titel = "Atemübung gegen stressbedingten Schwindel",
            voll =
                """
                Eine einfache Übung: 4 Sekunden einatmen, 4 Sekunden halten, 6 Sekunden ausatmen, mehrfach wiederholen. Das verlangsamt eine zu flache, schnelle Atmung und wirkt unmittelbar beruhigend auf das Nervensystem – hilfreich bei Schwindel, der mit Anspannung oder Angst einhergeht.
                """,
            peer =
                """
                Wenn bei mir Stress mit reinspielte, hat mir eine einfache Atemübung geholfen: 4 Sekunden einatmen, 4 Sekunden halten, 6 Sekunden ausatmen, mehrfach wiederholen. Wirkt unmittelbar beruhigend.
                """,
        ) +
        uebung(
            id = "teilE-uebung-schlafposition",
            titel = "Schlafposition und Kissenhöhe",
            voll =
                """
                Ein zu hohes oder zu flaches Kissen kann die Halswirbelsäule über Nacht in eine ungünstige Position bringen. Es gibt keine "richtige" Kissenhöhe für alle – probiere in kleinen Schritten und beobachte über mehrere Nächte, ob sich Nackenverspannung oder morgendlicher Schwindel verändert. Trage das in deinem Journal mit ein.
                """,
            peer =
                """
                Ein zu hohes oder zu flaches Kissen kann deinen Nacken über Nacht ungünstig belasten. Es gibt keine "richtige" Höhe für alle – probier in kleinen Schritten und beobachte über mehrere Nächte, ob sich was verändert.
                """,
        ) +
        uebung(
            id = "teilE-uebung-ergonomie",
            titel = "Ergonomie am Bildschirm",
            voll =
                """
                Bildschirmoberkante etwa auf Augenhöhe, regelmäßige Haltungswechsel und kurze Pausen alle 45–60 Minuten reduzieren die Dauerbelastung der oberen Halswirbelsäule – genau der Bereich, um den es in Teil B ging.
                """,
            peer =
                """
                Bildschirmoberkante auf Augenhöhe, regelmäßig die Haltung wechseln, alle 45–60 Minuten kurz aufstehen – genau der Bereich, der bei mir am meisten Verspannung erzeugt hat, wenn ich ihn vernachlässigt habe.
                """,
        ) +
        uebung(
            id = "teilE-uebung-kreislauf-fluessigkeit",
            titel = "Kreislauf und Flüssigkeitshaushalt",
            voll =
                """
                Ausreichend trinken (individuell verschieden, als grobe Orientierung oft 1,5–2 Liter über den Tag verteilt, außer bei ärztlich anderslautender Empfehlung), regelmäßige Mahlzeiten, und beim Aufstehen aus dem Liegen kurz auf der Bettkante sitzen bleiben, bevor du dich hinstellst – das gibt deinem Kreislauf Zeit, sich anzupassen.
                """,
            peer =
                """
                Ausreichend trinken, regelmäßig essen, und beim Aufstehen kurz auf der Bettkante sitzen bleiben, bevor du dich hinstellst – kleine Gewohnheit, spürbarer Unterschied für den Kreislauf.
                """,
        )
