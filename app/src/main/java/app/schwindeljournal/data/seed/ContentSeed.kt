package app.schwindeljournal.data.seed

import app.schwindeljournal.data.local.entity.ContentBlockEntity
import app.schwindeljournal.data.model.BuchTeil
import app.schwindeljournal.data.model.Modus
import app.schwindeljournal.data.model.Sprache
import app.schwindeljournal.data.model.VariantenTiefe

/**
 * Statischer, mit der App ausgelieferter Wissens-Bibliothek-Inhalt (Phase 3). Wortlaut
 * 1:1 aus manuskript-entwurf.md uebernommen - insbesondere der Warnzeichen-Block darf
 * laut CLAUDE.md/datenmodell-und-content-mapping.md nie gekuerzt oder umformuliert
 * werden. Weitere Buchteile (B-H, siehe datenmodell-und-content-mapping.md Abschnitt 3)
 * folgen als spaetere Ergaenzungen dieser Liste - keine neue Migration noetig, siehe
 * ContentBlockEntity-Doku.
 */
object ContentSeed {
    val alleBloecke: List<ContentBlockEntity> =
        listOf(warnzeichenBlock, warnzeichenBlockEn) +
            teilABloecke +
            teilBBloecke +
            teilCBloecke +
            teilDBloecke +
            teilEBloecke +
            teilFBloecke +
            teilGBloecke +
            teilHBloecke +
            teilABloeckeEn +
            teilBBloeckeEn +
            teilCBloeckeEn +
            teilDBloeckeEn +
            teilEBloeckeEn +
            teilFBloeckeEn +
            teilGBloeckeEn +
            teilHBloeckeEn
}

private val warnzeichenBlock =
    ContentBlockEntity(
        id = "teilA-warnzeichen",
        buchTeil = BuchTeil.A,
        titel = "Wann Schwindel zum Notfall wird",
        variantenTiefe = VariantenTiefe.VOLL,
        sichtbarInModus = setOf(Modus.KOMPASS, Modus.PEER, Modus.QUICK),
        istWarnzeichenInhalt = true,
        inhaltMarkdown =
            """
            Die allermeisten Schwindelursachen sind unangenehm, aber nicht akut gefährlich – genau für diese ist das Journal in diesem Buch gedacht. Es gibt jedoch eine kleine Gruppe von Ursachen, bei denen jede Verzögerung riskant sein kann, etwa ein Schlaganfall, eine Herzrhythmusstörung oder eine schwere Infektion. Diese Warnzeichen solltest du kennen, damit du im Zweifel nicht abwartest, sondern handelst:

            ### Sofort den Notruf (112) wählen oder in die Notaufnahme, wenn zum Schwindel zusätzlich eines der Folgenden auftritt:

            - Plötzliche Schwäche oder Taubheitsgefühl in Arm, Bein oder Gesicht, meist einseitig
            - Plötzliche Sprach- oder Sprechstörung, hängender Mundwinkel – merke dir dafür den **FAST-Test**: **F**ace (Gesicht – hängt ein Mundwinkel?), **A**rms (Arme – kann die Person beide Arme gleich hochhalten?), **S**peech (Sprache – klingt sie verwaschen?), **T**ime (Zeit – bei Auffälligkeit sofort den Notruf wählen)
            - Plötzliche, sehr starke Kopfschmerzen, wie noch nie zuvor erlebt
            - Sehstörungen wie Doppelbilder oder plötzlicher Sehverlust, zusätzlich zum Schwindel
            - Bewusstseinstrübung, Verwirrtheit oder Ohnmacht
            - Brustschmerzen, Herzrasen/spürbares Herzstolpern oder starke Atemnot zusammen mit dem Schwindel
            - Plötzlicher, einseitiger Hörverlust zusammen mit starkem Drehschwindel
            - Hohes Fieber und Nackensteifigkeit zusammen mit dem Schwindel

            ### Zeitnah (innerhalb weniger Tage) ärztlich abklären lassen, auch ohne die obigen Warnzeichen:

            - Schwindel tritt zum ersten Mal in deinem Leben auf
            - Schwindel wird von Mal zu Mal stärker oder hält immer länger an
            - Du bist gestürzt oder hattest beinahe einen Sturz durch den Schwindel

            Dieses Buch und sein Journal sind für die Zeit nach dieser Abklärung gedacht – als Werkzeug für das, was danach an Beobachtung und Verlaufskontrolle bleibt, nicht als Ersatz für den ersten wichtigen Schritt zum Arzt.
            """.trimIndent(),
    )

// Sicherheitskritisch: Wortlaut zum FAST-Test lehnt sich bewusst an etablierte,
// oeffentlich verbreitete Schlaganfall-Aufklaerung an (F.A.S.T. - Face, Arms, Speech,
// Time - American Stroke Association/CDC), statt frei zu uebersetzen. Restlicher
// Warnzeichen-Text ist ein KI-Entwurf, fachlich unspezifischer als der FAST-Teil.
private val warnzeichenBlockEn =
    ContentBlockEntity(
        id = "teilA-warnzeichen-en",
        buchTeil = BuchTeil.A,
        titel = "When vertigo becomes an emergency",
        variantenTiefe = VariantenTiefe.VOLL,
        sichtbarInModus = setOf(Modus.KOMPASS, Modus.PEER, Modus.QUICK),
        istWarnzeichenInhalt = true,
        sprache = Sprache.EN,
        inhaltMarkdown =
            """
            Most causes of vertigo and dizziness are unpleasant but not acutely dangerous – this journal is meant exactly for those. There is, however, a small group of causes where any delay can be risky, such as a stroke, a heart rhythm disorder, or a severe infection. You should know these warning signs so that, if in doubt, you act instead of waiting:

            ### Call emergency services immediately or go to the emergency room if, along with the vertigo/dizziness, any of the following occurs:

            - Sudden weakness or numbness in the arm, leg, or face, usually on one side
            - Sudden trouble speaking or slurred speech, a drooping side of the face – remember the **FAST test**: **F**ace (does one side of the face droop?), **A**rms (can the person raise both arms evenly?), **S**peech (is speech slurred or strange?), **T**ime (if any of this occurs, call emergency services right away)
            - Sudden, very severe headache, unlike anything experienced before
            - Vision problems such as double vision or sudden vision loss, in addition to the dizziness
            - Clouded consciousness, confusion, or fainting
            - Chest pain, a racing or noticeably irregular heartbeat, or severe shortness of breath together with the dizziness
            - Sudden, one-sided hearing loss together with severe spinning vertigo
            - High fever and neck stiffness together with the dizziness

            ### See a doctor promptly (within a few days), even without the warning signs above, if:

            - This is the first time vertigo/dizziness has ever happened to you
            - The episodes are getting stronger or lasting longer each time
            - You fell, or nearly fell, because of the dizziness

            This book and its journal are meant for the time after this initial medical check – as a tool for the observation and monitoring that follows, not as a substitute for that first, important step of seeing a doctor.
            """.trimIndent(),
    )
