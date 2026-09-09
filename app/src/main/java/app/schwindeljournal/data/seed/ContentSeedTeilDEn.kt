package app.schwindeljournal.data.seed

import app.schwindeljournal.data.local.entity.ContentBlockEntity
import app.schwindeljournal.data.model.BuchTeil
import app.schwindeljournal.data.model.Modus
import app.schwindeljournal.data.model.Sprache
import app.schwindeljournal.data.model.VariantenTiefe

internal val teilDBloeckeEn: List<ContentBlockEntity> =
    listOf(
        ContentBlockEntity(
            id = "teilD-beispiele-en",
            buchTeil = BuchTeil.D,
            titel = "Weak vs. strong journal entry",
            variantenTiefe = VariantenTiefe.VOLL,
            sichtbarInModus = setOf(Modus.KOMPASS),
            sprache = Sprache.EN,
            inhaltMarkdown =
                """
                Most people sitting in front of a doctor for the first time because of dizziness describe it roughly like this: "I got dizzy, it happened a few times, sometimes worse, sometimes less." That's honest – and almost worthless for sorting things out. Not because the description is wrong, but because it lacks the details that make a difference.

                ### Examples
                - Weak: "Felt dizzy in the morning" – Strong: "10:15 am, when standing up from the desk after 2 hrs of screen work, spinning vertigo for about 20 sec, followed by neck tension on the right, no nausea"
                - Weak: "Was a bad day" – Strong: "Dizziness occurred 3 times, only 5 hrs of sleep the night before, on a new, flatter pillow"

                The difference isn't in how much text there is, but in four things: **timing, triggering situation, exact duration, accompanying circumstances.** These are exactly the columns in your journal – they don't exist to create bureaucracy, but because each of them helps rule a possible cause in or out.

                ### Stay consistent – even on "nothing happened" days
                A common mistake: only the days with dizziness get documented, calm days stay blank. That loses the most important information – the contrast. So log at least the day rating every day, even when nothing happened. A pattern like "always dizzy after short nights" can only be recognized if you also document the nights with good sleep.
                """.trimIndent(),
        ),
        ContentBlockEntity(
            id = "teilD-beispiele-peer-en",
            buchTeil = BuchTeil.D,
            titel = "Weak vs. strong journal entry",
            variantenTiefe = VariantenTiefe.PEER,
            sichtbarInModus = setOf(Modus.PEER),
            sprache = Sprache.EN,
            inhaltMarkdown =
                """
                At first I also just wrote "was a bad day". That's almost useless. It was only once I started noting the time, the exact situation, duration, and accompanying circumstances that a pattern showed up for me. Small effort, big difference.
                """.trimIndent(),
        ),
        ContentBlockEntity(
            id = "teilD-checkliste-en",
            buchTeil = BuchTeil.D,
            titel = "What patients and doctors actually need",
            variantenTiefe = VariantenTiefe.VOLL,
            sichtbarInModus = setOf(Modus.KOMPASS),
            sprache = Sprache.EN,
            inhaltMarkdown =
                """
                What's especially valuable for the treating professional – more than any single description:

                - The **pattern over time**, not the single incident
                - The **exact duration** of an episode: seconds, minutes, or hours are diagnostically very different
                - A quick confirmation that **none of the warning signs** from Part A have occurred – this significantly speeds up the initial assessment
                - Whether the dizziness can be triggered by head/body position or also occurs at rest
                - The **profile page** – medications and pre-existing conditions are already documented in advance instead of having to be asked about during the appointment
                """.trimIndent(),
        ),
        ContentBlockEntity(
            id = "teilD-checkliste-peer-en",
            buchTeil = BuchTeil.D,
            titel = "What patients and doctors actually need",
            variantenTiefe = VariantenTiefe.PEER,
            sichtbarInModus = setOf(Modus.PEER),
            sprache = Sprache.EN,
            inhaltMarkdown =
                """
                From my own experience: what helped my doctor the most wasn't the description of a single incident, but the pattern over several weeks – plus the clear statement that none of the warning signs had occurred. That sped up the conversation enormously.
                """.trimIndent(),
        ),
    )
