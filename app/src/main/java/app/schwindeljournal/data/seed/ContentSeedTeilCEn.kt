package app.schwindeljournal.data.seed

import app.schwindeljournal.data.local.entity.ContentBlockEntity
import app.schwindeljournal.data.model.BuchTeil
import app.schwindeljournal.data.model.Modus
import app.schwindeljournal.data.model.Sprache
import app.schwindeljournal.data.model.VariantenTiefe

private fun ursacheEn(
    id: String,
    titel: String,
    voll: String,
    peer: String,
): List<ContentBlockEntity> =
    listOf(
        ContentBlockEntity(
            id = id,
            buchTeil = BuchTeil.C,
            titel = titel,
            variantenTiefe = VariantenTiefe.VOLL,
            sichtbarInModus = setOf(Modus.KOMPASS),
            sprache = Sprache.EN,
            inhaltMarkdown = voll.trimIndent(),
        ),
        ContentBlockEntity(
            id = "$id-peer",
            buchTeil = BuchTeil.C,
            titel = titel,
            variantenTiefe = VariantenTiefe.PEER,
            sichtbarInModus = setOf(Modus.PEER),
            sprache = Sprache.EN,
            inhaltMarkdown = peer.trimIndent(),
        ),
    )

internal val teilCBloeckeEn: List<ContentBlockEntity> =
    ursacheEn(
        id = "teilC-ursache-lagerungsschwindel-en",
        titel = "Benign positional vertigo (BPPV)",
        voll =
            """
            Small calcium crystals in your inner ear, which normally sit firmly in place, can come loose and drift into the semicircular canals. When you move your head – for example turning over in bed or bending backward – these crystals "slosh" and signal a movement to your brain that isn't actually happening. Typical: a brief but intense spinning vertigo lasting a few seconds up to at most a minute, triggered by a specific head position, which then fades on its own.
            """,
        peer =
            """
            Know that feeling – you turn over in bed, and for a few seconds everything spins violently? That can be caused by small, displaced calcium crystals in your inner ear. The reassuring part: it usually fades on its own within a minute and is very treatable.
            """,
    ) +
        ursacheEn(
            id = "teilC-ursache-meniere-en",
            titel = "Meniere's disease",
            voll =
                """
                Here, the fluid pressure inside the inner ear changes. Attacks are typically described as lasting minutes to several hours, often accompanied by tinnitus, a feeling of pressure in the ear, and temporary hearing loss on one side.
                """,
            peer =
                """
                With Meniere's disease, the fluid pressure inside the inner ear changes. Typical are attacks that can last minutes to hours, often together with tinnitus and a feeling of pressure in the ear. If this sounds familiar, that's a good point to bring up with an ENT doctor.
                """,
        ) +
        ursacheEn(
            id = "teilC-ursache-neuritis-vestibularis-en",
            titel = "Vestibular neuritis (vestibular failure)",
            voll =
                """
                A (usually presumed viral) inflammation of the vestibular nerve. Typical: sudden, very intense, constant dizziness over several days, often with nausea and vomiting, which – unlike BPPV – cannot be triggered by a specific head position but is simply constantly present, gradually improving over days to weeks.
                """,
            peer =
                """
                A sudden, very intense, constant dizziness over several days, often with nausea – unlike positional vertigo, it can't be triggered by a specific head position, it's just there. This can be caused by inflammation of the vestibular nerve. That sounds frightening, but it usually improves over days to weeks.
                """,
        ) +
        ursacheEn(
            id = "teilC-ursache-migraene-en",
            titel = "Migraine-associated dizziness",
            voll =
                """
                Not every migraine shows up as a classic headache – in some people, dizziness dominates, sometimes even without any headache at all. Typical: episodic occurrence, often with sensitivity to light or sound, sometimes with an "aura" (visual disturbances) beforehand.
                """,
            peer =
                """
                Not every migraine shows up as a headache – for some people dizziness dominates, even completely without headache. If your dizziness comes in episodes and you're also sensitive to light or sound, that could be a clue.
                """,
        ) +
        ursacheEn(
            id = "teilC-ursache-kreislauf-en",
            titel = "Circulation, blood sugar, fluid balance",
            voll =
                """
                Blood pressure that's too low on standing up (orthostatic hypotension), not drinking enough, not eating for too long – all of this can show up as lightheadedness, your vision going dark, or "weak knees". Usually brief, often reproducible (e.g. always on standing up quickly), and exactly for that reason a good example of how valuable the blood pressure entry in your profile can be.
                """,
            peer =
                """
                Stood up quickly and your vision briefly went dark? Many people know that feeling. Not drinking enough, not eating for too long, or simply a brief drop in blood pressure – this usually shows up as lightheadedness, less often as classic spinning vertigo. Enter your blood pressure in your profile, it helps with sorting things out.
                """,
        ) +
        ursacheEn(
            id = "teilC-ursache-medikamente-en",
            titel = "Medication side effects",
            voll =
                """
                Blood pressure medications, certain antibiotics, sedatives, painkillers, and many other drug classes can have dizziness as a side effect – individually, and especially in combination with several medications. That's why your profile asks you to: really enter every medication, including over-the-counter ones.
                """,
            peer =
                """
                I wouldn't have guessed this before I looked into it: quite a lot of medications can have dizziness as a side effect – blood pressure medication, sleeping pills, painkillers, some antibiotics, and especially in combination. So the tip from your profile: really enter every medication, including over-the-counter ones.
                """,
        ) +
        ursacheEn(
            id = "teilC-ursache-angst-stress-en",
            titel = "Anxiety- and stress-related dizziness",
            voll =
                """
                Ongoing tension, worry, or an acute anxiety reaction can lead to lightheadedness via changed breathing (often shallower and faster, sometimes toward hyperventilation). For those affected, this often feels just as "real" as physically caused dizziness – because it is exactly that, just with a different trigger.
                """,
            peer =
                """
                Ongoing stress or an acute anxiety reaction can show up as dizziness or lightheadedness via changed breathing. This isn't "imagined" dizziness – it's a real physical reaction, just as much to be taken seriously as any other cause in this book.
                """,
        ) +
        listOf(
            ContentBlockEntity(
                id = "teilC-tabelle-en",
                buchTeil = BuchTeil.C,
                titel = "Orientation table",
                variantenTiefe = VariantenTiefe.VOLL,
                sichtbarInModus = setOf(Modus.KOMPASS),
                sprache = Sprache.EN,
                inhaltMarkdown =
                    """
                    An initial orientation by typical duration and trigger – deliberately not a checklist for self-diagnosis; in practice the boundaries are often blurred, and several causes can be present at once.

                    - Positional vertigo (BPPV): seconds to about 1 minute, triggered by a specific head movement
                    - Meniere's disease: minutes to hours, often without an identifiable trigger
                    - Vestibular neuritis: days (constant state), no movement trigger needed
                    - Migraine-associated: minutes to hours, episodic, migraine-typical triggers
                    - Circulation/blood pressure: seconds to a few minutes, standing up, heat, not enough fluids
                    - Medication side effect: variable, often ongoing for as long as taken, after taking/starting a new medication
                    - Anxiety-/stress-related: minutes, situation-dependent, stressful situation/tension
                    - Cervicogenic (Part B): variable, often minutes, head/neck movement, tension
                    """.trimIndent(),
            ),
            ContentBlockEntity(
                id = "teilC-tabelle-peer-en",
                buchTeil = BuchTeil.C,
                titel = "Orientation table",
                variantenTiefe = VariantenTiefe.PEER,
                sichtbarInModus = setOf(Modus.PEER),
                sprache = Sprache.EN,
                inhaltMarkdown =
                    """
                    At some point I put together a table like this myself to keep track – duration and trigger are often the best first clue as to which causes are even worth considering. Think of it as a rough compass, not a diagnostic tool.
                    """.trimIndent(),
            ),
        )
