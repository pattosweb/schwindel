package app.schwindeljournal.data.seed

import app.schwindeljournal.data.local.entity.ContentBlockEntity
import app.schwindeljournal.data.model.BuchTeil
import app.schwindeljournal.data.model.Modus
import app.schwindeljournal.data.model.Sprache
import app.schwindeljournal.data.model.VariantenTiefe

internal val teilFBloeckeEn: List<ContentBlockEntity> =
    listOf(
        ContentBlockEntity(
            id = "teilF-tabelle-en",
            buchTeil = BuchTeil.F,
            titel = "Observation → possible specialist",
            variantenTiefe = VariantenTiefe.VOLL,
            sichtbarInModus = setOf(Modus.KOMPASS),
            sprache = Sprache.EN,
            inhaltMarkdown =
                """
                Whatever you observe in yourself: your GP is almost always the right first point of contact. From there, a referral to the appropriate specialty follows if needed. This table doesn't replace the conversation with your GP – it helps you put your own observations into words and ask more targeted questions.

                - Spinning vertigo, tinnitus, hearing changes → ENT doctor
                - Neck pain, tension, dizziness on head movement → physiotherapist, chiropractor, orthopedist
                - Headache patterns, neurological accompanying symptoms → neurologist
                - Heart palpitations, chest pain, dizziness on exertion → cardiologist
                - Dizziness related to new medications → GP (medication review)
                - Dizziness closely tied to stress/anxiety → GP, possibly psychotherapeutic support
                """.trimIndent(),
        ),
        ContentBlockEntity(
            id = "teilF-tabelle-peer-en",
            buchTeil = BuchTeil.F,
            titel = "Observation → possible specialist",
            variantenTiefe = VariantenTiefe.PEER,
            sichtbarInModus = setOf(Modus.PEER),
            sprache = Sprache.EN,
            inhaltMarkdown =
                """
                My GP was always the first step for me. From there it continued depending on what I observed – ENT, physio/chiro, or neurology. The table in the book helps you translate your own observation in the right direction.
                """.trimIndent(),
        ),
        ContentBlockEntity(
            id = "teilF-mitnehmen-en",
            buchTeil = BuchTeil.F,
            titel = "What to bring to your appointment",
            variantenTiefe = VariantenTiefe.VOLL,
            sichtbarInModus = setOf(Modus.KOMPASS),
            sprache = Sprache.EN,
            inhaltMarkdown =
                """
                ### What to bring to your appointment
                - Your completed profile page
                - Your journal (or the final summary, if the 60 days are already up)
                - If available: the PDF export from the Vertigo Journal app

                ### What you can bring up there
                - "I've observed over X days/weeks that..." (name a concrete pattern)
                - "Could my cervical spine be playing a role? I've made these observations about it..." (Part B as a conversation starter, not a diagnosis)
                - "Which of the causes from my overview (Part C) could apply to me, and how do we narrow it down?"
                """.trimIndent(),
        ),
        ContentBlockEntity(
            id = "teilF-mitnehmen-peer-en",
            buchTeil = BuchTeil.F,
            titel = "What to bring to your appointment",
            variantenTiefe = VariantenTiefe.PEER,
            sichtbarInModus = setOf(Modus.PEER),
            sprache = Sprache.EN,
            inhaltMarkdown =
                """
                I learned this: profile, journal (or the summary), and the app export if I have it – I've brought that to every appointment since. Saves time and I don't forget anything important.
                """.trimIndent(),
        ),
    )
