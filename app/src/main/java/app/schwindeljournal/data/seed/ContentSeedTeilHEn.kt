package app.schwindeljournal.data.seed

import app.schwindeljournal.data.local.entity.ContentBlockEntity
import app.schwindeljournal.data.model.BuchTeil
import app.schwindeljournal.data.model.Modus
import app.schwindeljournal.data.model.Sprache
import app.schwindeljournal.data.model.VariantenTiefe

private fun glossarEn(
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
            sprache = Sprache.EN,
            inhaltMarkdown = definition,
        ),
        ContentBlockEntity(
            id = "$id-kurz",
            buchTeil = BuchTeil.H,
            titel = begriff,
            variantenTiefe = VariantenTiefe.KURZ,
            sichtbarInModus = setOf(Modus.QUICK),
            sprache = Sprache.EN,
            inhaltMarkdown = kurz,
        ),
    )

internal val teilHBloeckeEn: List<ContentBlockEntity> =
    glossarEn(
        "teilH-glossar-vertigo-en",
        "Vertigo",
        "Dizziness with an actually perceived illusion of movement (spinning, tilting, swaying).",
        "Dizziness with an actual illusion of movement (spinning/tilting).",
    ) +
        glossarEn(
            "teilH-glossar-dizziness-en",
            "Dizziness",
            "Lightheadedness, \"emptiness in the head\", without any perceived movement.",
            "Lightheadedness without perceived movement.",
        ) +
        glossarEn(
            "teilH-glossar-vestibularorgan-en",
            "Vestibular organ",
            "The balance organ in the inner ear.",
            "Balance organ in the inner ear.",
        ) +
        glossarEn(
            "teilH-glossar-atlas-en",
            "Atlas (C1)",
            "The topmost cervical vertebra, carries the head and enables most of the head's rotation.",
            "Topmost cervical vertebra, carries the head.",
        ) +
        glossarEn(
            "teilH-glossar-propriozeption-en",
            "Proprioception",
            "The body's ability to sense its own position and movement in space, among other " +
                "things via sensors in the neck area.",
            "The body's own sense of position/movement.",
        ) +
        glossarEn(
            "teilH-glossar-zervikogener-schwindel-en",
            "Cervicogenic dizziness",
            "Dizziness suspected to originate in the cervical spine; not undisputed in medicine.",
            "Possible origin in the cervical spine (disputed).",
        ) +
        glossarEn(
            "teilH-glossar-bppv-en",
            "BPPV (benign positional vertigo)",
            "Brief spinning vertigo triggered by a specific head movement, caused by displaced " +
                "calcium crystals in the inner ear.",
            "Brief spinning vertigo from displaced calcium crystals in the inner ear.",
        ) +
        glossarEn(
            "teilH-glossar-morbus-meniere-en",
            "Meniere's disease",
            "A condition involving pressure changes in the inner ear, typically with tinnitus " +
                "and temporary hearing loss.",
            "Inner-ear pressure change, often with tinnitus/hearing loss.",
        ) +
        glossarEn(
            "teilH-glossar-neuritis-vestibularis-en",
            "Vestibular neuritis",
            "Inflammation of the vestibular nerve with persistent, constant dizziness over " +
                "several days.",
            "Inflammation of the vestibular nerve, constant dizziness over days.",
        ) +
        glossarEn(
            "teilH-glossar-orthostatische-hypotonie-en",
            "Orthostatic hypotension",
            "Drop in blood pressure on standing up, a common cause of brief lightheadedness.",
            "Drop in blood pressure on standing up.",
        ) +
        glossarEn(
            "teilH-glossar-nystagmus-en",
            "Nystagmus",
            "Involuntary, rhythmic eye movement, often accompanying balance disorders.",
            "Involuntary, rhythmic eye movement.",
        )
