package app.schwindeljournal.data.seed

import app.schwindeljournal.data.local.entity.ContentBlockEntity
import app.schwindeljournal.data.model.BuchTeil
import app.schwindeljournal.data.model.Modus
import app.schwindeljournal.data.model.VariantenTiefe

/**
 * Glossar (datenmodell-und-content-mapping.md Abschnitt 3: "Ja, alle" - in jedem
 * Modus sichtbar, aber NICHT mit dem Warnzeichen-Override, sondern normal nach
 * variantenTiefe gefiltert: Kompass/Peer teilen sich die VOLL-Definition (content-
 * varianten-texte.md: "PEER = identisch mit VOLL"), Quick sieht die KURZ-Fassung.
 */
private fun glossar(
    id: String,
    begriff: String,
    definition: String,
    kurz: String,
): List<ContentBlockEntity> =
    listOf(
        ContentBlockEntity(
            id = id,
            buchTeil = BuchTeil.H,
            titel = begriff,
            variantenTiefe = VariantenTiefe.VOLL,
            sichtbarInModus = setOf(Modus.KOMPASS, Modus.PEER),
            inhaltMarkdown = definition,
        ),
        ContentBlockEntity(
            id = "$id-kurz",
            buchTeil = BuchTeil.H,
            titel = begriff,
            variantenTiefe = VariantenTiefe.KURZ,
            sichtbarInModus = setOf(Modus.QUICK),
            inhaltMarkdown = kurz,
        ),
    )

internal val teilHBloecke: List<ContentBlockEntity> =
    glossar(
        "teilH-glossar-vertigo",
        "Vertigo",
        "Schwindel mit tatsächlich wahrgenommener Scheinbewegung (Drehen, Kippen, Schwanken).",
        "Schwindel mit tatsächlicher Scheinbewegung (Drehen/Kippen).",
    ) +
        glossar(
            "teilH-glossar-dizziness",
            "Dizziness",
            "Benommenheit, \"Leere im Kopf\", ohne wahrgenommene Bewegung.",
            "Benommenheit ohne wahrgenommene Bewegung.",
        ) +
        glossar(
            "teilH-glossar-vestibularorgan",
            "Vestibularorgan",
            "Das Gleichgewichtsorgan im Innenohr.",
            "Gleichgewichtsorgan im Innenohr.",
        ) +
        glossar(
            "teilH-glossar-atlas",
            "Atlas (C1)",
            "Der oberste Halswirbel, trägt den Kopf und ermöglicht einen Großteil der Kopfdrehung.",
            "Oberster Halswirbel, trägt den Kopf.",
        ) +
        glossar(
            "teilH-glossar-propriozeption",
            "Propriozeption",
            "Die Fähigkeit des Körpers, die eigene Position und Bewegung im Raum wahrzunehmen, " +
                "u. a. über Sensoren im Nackenbereich.",
            "Körpereigene Wahrnehmung von Position/Bewegung.",
        ) +
        glossar(
            "teilH-glossar-zervikogener-schwindel",
            "Zervikogener Schwindel",
            "Schwindel, dessen Ursprung im Bereich der Halswirbelsäule vermutet wird; in der " +
                "Medizin nicht unumstritten.",
            "Möglicher Ursprung im Bereich der Halswirbelsäule (umstritten).",
        ) +
        glossar(
            "teilH-glossar-bppv",
            "BPPV (Lagerungsschwindel)",
            "Kurzer, durch bestimmte Kopfbewegung ausgelöster Drehschwindel durch verlagerte " +
                "Kalkkristalle im Innenohr.",
            "Kurzer Drehschwindel durch verlagerte Kalkkristalle im Innenohr.",
        ) +
        glossar(
            "teilH-glossar-morbus-meniere",
            "Morbus Menière",
            "Erkrankung mit Druckveränderung im Innenohr, typischerweise mit Ohrensausen und " +
                "vorübergehendem Hörverlust.",
            "Innenohr-Druckveränderung, oft mit Ohrensausen/Hörverlust.",
        ) +
        glossar(
            "teilH-glossar-neuritis-vestibularis",
            "Neuritis vestibularis",
            "Entzündung des Gleichgewichtsnervs mit anhaltendem Dauerschwindel über mehrere Tage.",
            "Entzündung des Gleichgewichtsnervs, Dauerschwindel über Tage.",
        ) +
        glossar(
            "teilH-glossar-orthostatische-hypotonie",
            "Orthostatische Hypotonie",
            "Blutdruckabfall beim Aufstehen, häufige Ursache für kurze Benommenheit.",
            "Blutdruckabfall beim Aufstehen.",
        ) +
        glossar(
            "teilH-glossar-nystagmus",
            "Nystagmus",
            "Unwillkürliche, rhythmische Augenbewegung, oft begleitend bei Gleichgewichtsstörungen.",
            "Unwillkürliche, rhythmische Augenbewegung.",
        )
