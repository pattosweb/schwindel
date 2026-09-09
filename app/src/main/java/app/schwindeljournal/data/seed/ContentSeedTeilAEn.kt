package app.schwindeljournal.data.seed

import app.schwindeljournal.data.local.entity.ContentBlockEntity
import app.schwindeljournal.data.model.BuchTeil
import app.schwindeljournal.data.model.Modus
import app.schwindeljournal.data.model.Sprache
import app.schwindeljournal.data.model.VariantenTiefe

// KI-Uebersetzung (Phase 5 Grundgeruest+Englisch). Nicht sicherheitskritisch wie der
// Warnzeichen-Block (siehe ContentSeed.kt) - normale redaktionelle Inhalte.
internal val teilABloeckeEn: List<ContentBlockEntity> =
    listOf(
        ContentBlockEntity(
            id = "teilA-begriffe-en",
            buchTeil = BuchTeil.A,
            titel = "Vertigo vs. Dizziness",
            variantenTiefe = VariantenTiefe.VOLL,
            sichtbarInModus = setOf(Modus.KOMPASS),
            sprache = Sprache.EN,
            inhaltMarkdown =
                """
                In everyday German, people say "Schwindel" for almost everything – whether the room is spinning, the floor seems to tilt, or you just feel vaguely "not quite there". English already makes a useful distinction here: **Vertigo** means an actual perceived illusion of movement – things spin, tilt, or sway. **Dizziness** tends to mean lightheadedness, a feeling of emptiness in the head, or "not quite there", without anything actually appearing to move. If you can tell these apart for yourself, it helps any doctor enormously in tracking down the cause – so always note in your journal which of the two you experienced.
                """.trimIndent(),
        ),
        ContentBlockEntity(
            id = "teilA-begriffe-peer-en",
            buchTeil = BuchTeil.A,
            titel = "Vertigo vs. Dizziness",
            variantenTiefe = VariantenTiefe.PEER,
            sichtbarInModus = setOf(Modus.PEER),
            sprache = Sprache.EN,
            inhaltMarkdown =
                """
                Remember how hard it was to explain to someone what you were feeling? For me, that was exactly where it started. I learned to tell two things apart: does the room actually seem to spin, as if you were turning, even though you're standing still? That's vertigo. Or is it more a feeling of cotton wool in your head, of "not quite there", without anything actually moving? That's more like dizziness. Both are real, both count – but if you can tell them apart for yourself, it helps any doctor listen to you properly.
                """.trimIndent(),
        ),
        ContentBlockEntity(
            id = "teilA-familien-en",
            buchTeil = BuchTeil.A,
            titel = "The four big families",
            variantenTiefe = VariantenTiefe.VOLL,
            sichtbarInModus = setOf(Modus.KOMPASS),
            sprache = Sprache.EN,
            inhaltMarkdown =
                """
                Almost every case of vertigo or dizziness can be roughly assigned to one of four groups. This doesn't replace a diagnosis, but it gives you an initial map:

                ### 1. Vestibular vertigo – the inner ear
                Your inner ear contains a delicate system of canals and sensory organs that registers how your head is moving and where "up" is. If this system is irritated or disturbed – for example by displaced calcium crystals (as in benign positional vertigo), inflammation of the vestibular nerve, or pressure changes in the inner ear (as in Meniere's disease) – it usually causes a clear spinning vertigo, often tied to head movement.

                ### 2. Cervicogenic dizziness – the cervical spine
                Your neck constantly reports to your brain how your head is positioned in space. Tension, poor posture, or old injuries in this area can distort these signals and trigger a feeling of dizziness even though your inner ear is perfectly healthy.

                ### 3. Circulatory/cardiac dizziness
                If your blood pressure drops briefly – for example when standing up quickly, from lack of fluids, or due to certain medications – your brain briefly gets too little blood flow. This tends to show up as your vision going dark, lightheadedness (dizziness), or a feeling of "sinking", less often as classic spinning vertigo. Heart rhythm disorders can also present this way – which is why persistent or recurring dizziness of this kind should always be checked by a doctor.

                ### 4. Psychogenic/stress-related dizziness
                Ongoing stress, anxiety, or being overwhelmed can show up physically as dizziness or lightheadedness – often combined with shallow breathing or hyperventilation. This is not "imagined" dizziness; it's a real physical reaction to psychological strain, and just as much to be taken seriously as the other three families.

                In practice, these families often overlap – stress can worsen existing cervicogenic dizziness, an inner-ear problem can additionally trigger anxiety and thus psychogenic components. That's exactly why simply waiting it out or guessing rarely helps, and structured observation – like with this journal – makes the difference.
                """.trimIndent(),
        ),
        ContentBlockEntity(
            id = "teilA-familien-peer-en",
            buchTeil = BuchTeil.A,
            titel = "The four big families",
            variantenTiefe = VariantenTiefe.PEER,
            sichtbarInModus = setOf(Modus.PEER),
            sprache = Sprache.EN,
            inhaltMarkdown =
                """
                At first I thought dizziness was just dizziness. Over time I learned there are actually four pretty different "families". Sometimes it's your inner ear, sometimes your cervical spine, sometimes your circulation or your heart, and sometimes it's your nervous system reacting to stress. The tricky part: they can overlap and reinforce each other. That's exactly why pure guesswork rarely helps – observing does.
                """.trimIndent(),
        ),
    )
