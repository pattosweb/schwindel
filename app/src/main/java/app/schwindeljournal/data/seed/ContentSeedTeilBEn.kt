package app.schwindeljournal.data.seed

import app.schwindeljournal.data.local.entity.ContentBlockEntity
import app.schwindeljournal.data.model.BuchTeil
import app.schwindeljournal.data.model.Modus
import app.schwindeljournal.data.model.Sprache
import app.schwindeljournal.data.model.VariantenTiefe

internal val teilBBloeckeEn: List<ContentBlockEntity> =
    listOf(
        ContentBlockEntity(
            id = "teilB-atlas-en",
            buchTeil = BuchTeil.B,
            titel = "The atlas and proprioception",
            variantenTiefe = VariantenTiefe.VOLL,
            sichtbarInModus = setOf(Modus.KOMPASS),
            sprache = Sprache.EN,
            inhaltMarkdown =
                """
                Picture your cervical spine as a chain of seven building blocks that carry your head while keeping it mobile. The topmost of these blocks is called the atlas (in medical terms, C1) – named after the figure from Greek mythology who carries the sky. That's no coincidence: this very vertebra carries the weight of your head while also enabling much of your head's rotation.

                What most people don't know: this area contains an extremely high number of nerve cells that constantly report to your brain how your head is positioned in space – this is called proprioception. These signals converge in the same brain regions that also process the signals from your inner ear (your actual balance organ) and from your eyes. Your brain constantly cross-checks these three sources of information. If one of them sends "wrong" or contradictory signals – for example due to tension or misalignment in the upper neck area – this can result in a feeling of dizziness, even though your inner ear is perfectly healthy.
                """.trimIndent(),
        ),
        ContentBlockEntity(
            id = "teilB-atlas-peer-en",
            buchTeil = BuchTeil.B,
            titel = "The atlas and proprioception",
            variantenTiefe = VariantenTiefe.PEER,
            sichtbarInModus = setOf(Modus.PEER),
            sprache = Sprache.EN,
            inhaltMarkdown =
                """
                This genuinely surprised me the first time I heard it: the topmost vertebra of your cervical spine, the atlas, doesn't just carry your head – it constantly reports to your brain how your head is positioned in space. These reports land in the same area of your brain as the signals from your inner ear and your eyes. If these three sources don't agree – for example because your neck is tense – that can cause dizziness, even though nothing is wrong with your inner ear.
                """.trimIndent(),
        ),
        ContentBlockEntity(
            id = "teilB-zervikogen-en",
            buchTeil = BuchTeil.B,
            titel = "Cervicogenic dizziness",
            variantenTiefe = VariantenTiefe.VOLL,
            sichtbarInModus = setOf(Modus.KOMPASS),
            sprache = Sprache.EN,
            inhaltMarkdown =
                """
                There's a medical term for this type of dizziness, whose origin is suspected to lie in the cervical spine: cervicogenic dizziness ("cervical" = relating to the neck). Important to know: it is not undisputed in medicine – there is no single test that proves it beyond doubt. That's exactly why, as someone affected, careful observation – the kind you're doing with this journal – is worthwhile.

                Typical observations that *may* point to cervical spine involvement (without this being a diagnosis):

                - The dizziness tends to occur during or shortly after head/neck movements (e.g. looking back, looking up, long periods at a screen)
                - It's accompanied by neck tension, neck pain, or a feeling of "stiffness" in the shoulder/neck area
                - It gets worse after poor sleep or an unfamiliar pillow height
                - Headaches that pull from the neck toward the temple/forehead occur alongside it

                You'll deliberately find these points as their own columns in your journal – not so you can diagnose yourself, but so you can bring these observations, concretely and with examples, into a conversation with a doctor, physiotherapist, or chiropractor.

                ### Whiplash and old injuries
                Events further in the past can also play a role – a car accident years ago, a fall, an unnoticed strain over a long time (e.g. from one-sided posture at work). The connection between such an event and today's dizziness isn't always obvious, because a lot of time often lies between cause and symptom. If you've had such an event in the past, note it down separately – it's one of the first questions a therapist specializing in the cervical spine will ask you.
                """.trimIndent(),
        ),
        ContentBlockEntity(
            id = "teilB-zervikogen-peer-en",
            buchTeil = BuchTeil.B,
            titel = "Cervicogenic dizziness",
            variantenTiefe = VariantenTiefe.PEER,
            sichtbarInModus = setOf(Modus.PEER),
            sprache = Sprache.EN,
            inhaltMarkdown =
                """
                There's a name for what I went through: cervicogenic dizziness – dizziness whose origin is suspected to lie in the neck. Important to know: even doctors don't always agree on it, there's no clear-cut test for it. What helped me: looking closely. Does the dizziness tend to come during or after head movement? Is your neck tense at the same time? Does it get worse after poor sleep? These aren't diagnostic criteria, but they're good conversation starters for a doctor, physio, or chiropractor.
                """.trimIndent(),
        ),
        ContentBlockEntity(
            id = "teilB-skoliose-en",
            buchTeil = BuchTeil.B,
            titel = "Scoliosis and the vicious circle of tension",
            variantenTiefe = VariantenTiefe.VOLL,
            sichtbarInModus = setOf(Modus.KOMPASS),
            sprache = Sprache.EN,
            inhaltMarkdown =
                """
                It's not just the atlas alone – the alignment of the entire spine can also play a role. With scoliosis – a sideways curvature of the spine – often just a phase of little movement is enough for the supporting muscles to be worked unevenly, pulling the spine more strongly to one side. That increases tension, especially in the shoulder/neck area, which in turn makes moving more unpleasant – and that reduces movement further still. A vicious circle develops: guarding posture, more tension, less movement, even more tension. Because the upper cervical spine is so closely linked to your balance system, such chronic tension can indeed affect dizziness as well – not directly through the scoliosis itself, but via the tension it tends to promote. If you know you have scoliosis, it's worth noting in your profile and paying particular attention, when observing, to the connection between periods of movement (a lot/little) and how tense you feel.

                ### What this means for how you move forward
                This chapter isn't meant to convince you that "it's the neck". It's meant to give you an additional angle that many people affected only discover by chance after a long odyssey of doctor visits. Take the observation points from this chapter into your journal – and in Part F you'll learn which contact person can be the better first step for which observation.
                """.trimIndent(),
        ),
        ContentBlockEntity(
            id = "teilB-skoliose-peer-en",
            buchTeil = BuchTeil.B,
            titel = "Scoliosis and the vicious circle of tension",
            variantenTiefe = VariantenTiefe.PEER,
            sichtbarInModus = setOf(Modus.PEER),
            sprache = Sprache.EN,
            inhaltMarkdown =
                """
                This was the decisive point for me personally: I have mild scoliosis. If I don't use the muscles regularly, my spine pulls more strongly to one side – that creates tension, the tension makes movement more unpleasant, so I move even less, and the tension increases further. A real vicious circle. If you know you have scoliosis: try consciously observing whether periods of movement and how tense you feel are connected for you.
                """.trimIndent(),
        ),
    )
