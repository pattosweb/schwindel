package app.schwindeljournal.data.seed

import app.schwindeljournal.data.local.entity.ContentBlockEntity
import app.schwindeljournal.data.model.BuchTeil
import app.schwindeljournal.data.model.Modus
import app.schwindeljournal.data.model.Sprache
import app.schwindeljournal.data.model.VariantenTiefe

private fun uebungEn(
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
            sprache = Sprache.EN,
            inhaltMarkdown = voll.trimIndent(),
        ),
        ContentBlockEntity(
            id = "$id-peer",
            buchTeil = BuchTeil.E,
            titel = titel,
            variantenTiefe = VariantenTiefe.PEER,
            sichtbarInModus = setOf(Modus.PEER),
            sprache = Sprache.EN,
            inhaltMarkdown = peer.trimIndent(),
        ),
    )

private const val TEIL_E_INTRO_EN =
    "Everything in this chapter is a gentle, generally known self-help approach – not " +
        "treatment in the medical sense. If an acute or unclear cause of dizziness is " +
        "present, talk to your doctor or physiotherapist before trying anything, about " +
        "whether and which of the following exercises are suitable for you."

internal val teilEBloeckeEn: List<ContentBlockEntity> =
    uebungEn(
        id = "teilE-uebung-nackenmobilisation-en",
        titel = "Gentle neck mobilization",
        voll =
            """
            $TEIL_E_INTRO_EN

            Slow, small movements – turn your head gently left/right, tilt it gently to the side, each time only as far as feels comfortable, hold for a few breaths. The goal is mobility and relaxation of the neck muscles, not "cracking" or realigning vertebrae – that belongs exclusively in the hands of a trained professional.
            """,
        peer =
            """
            Small, gentle movements – turn your head gently, tilt it gently to the side, only as far as feels comfortable. No cracking, no realigning – that's a professional's job, not your own exercise.
            """,
    ) +
        uebungEn(
            id = "teilE-uebung-cawthorne-cooksey-en",
            titel = "Vestibular training according to Cawthorne-Cooksey",
            voll =
                """
                An exercise concept known for decades, with no fixed dogma: controlled eye movements (e.g. following a finger with your eyes while keeping your head still), then controlled head movements with a fixed gaze, later combinations of head and body movement while sitting and standing. The basic idea: your balance system "learns", through repeated, controlled stimulus, to cope better with conflicting signals. Build these exercises up slowly and only increase them if they're tolerated well.
                """,
            peer =
                """
                This exercise concept has been around for decades: first follow a finger with your eyes while keeping your head still, then head movements with a fixed gaze, later combinations while sitting and standing. The idea behind it: your balance system learns through repeated, controlled stimulus. Increase slowly, don't overdo it.
                """,
        ) +
        uebungEn(
            id = "teilE-uebung-atemuebung-en",
            titel = "Breathing exercise for stress-related dizziness",
            voll =
                """
                A simple exercise: inhale for 4 seconds, hold for 4 seconds, exhale for 6 seconds, repeat several times. This slows down breathing that's too shallow and fast, and has an immediate calming effect on the nervous system – helpful for dizziness that comes with tension or anxiety.
                """,
            peer =
                """
                When stress played a role for me, a simple breathing exercise helped: inhale for 4 seconds, hold for 4 seconds, exhale for 6 seconds, repeat several times. It has an immediate calming effect.
                """,
        ) +
        uebungEn(
            id = "teilE-uebung-schlafposition-en",
            titel = "Sleep position and pillow height",
            voll =
                """
                A pillow that's too high or too flat can put your cervical spine in an unfavorable position overnight. There's no single "right" pillow height for everyone – try small changes and observe over several nights whether neck tension or morning dizziness changes. Note this in your journal too.
                """,
            peer =
                """
                A pillow that's too high or too flat can strain your neck unfavorably overnight. There's no single "right" height for everyone – try small changes and observe over several nights whether anything changes.
                """,
        ) +
        uebungEn(
            id = "teilE-uebung-ergonomie-en",
            titel = "Screen ergonomics",
            voll =
                """
                Top of the screen roughly at eye level, regular posture changes, and short breaks every 45–60 minutes reduce the ongoing strain on the upper cervical spine – exactly the area Part B was about.
                """,
            peer =
                """
                Top of the screen at eye level, change your posture regularly, get up briefly every 45–60 minutes – exactly the area that caused me the most tension whenever I neglected it.
                """,
        ) +
        uebungEn(
            id = "teilE-uebung-kreislauf-fluessigkeit-en",
            titel = "Circulation and fluid balance",
            voll =
                """
                Drink enough (this varies by person, roughly 1.5–2 liters spread over the day as a general guide, unless your doctor advises otherwise), eat regular meals, and when getting up from lying down, sit on the edge of the bed for a moment before standing – that gives your circulation time to adjust.
                """,
            peer =
                """
                Drink enough, eat regularly, and when getting up, sit on the edge of the bed for a moment before standing – a small habit with a noticeable difference for your circulation.
                """,
        )
