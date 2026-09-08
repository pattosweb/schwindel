package app.schwindeljournal.data.seed

import app.schwindeljournal.data.local.entity.ContentBlockEntity
import app.schwindeljournal.data.model.BuchTeil
import app.schwindeljournal.data.model.Modus
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
        listOf(warnzeichenBlock) +
            teilABloecke +
            teilBBloecke +
            teilCBloecke +
            teilDBloecke +
            teilEBloecke +
            teilFBloecke +
            teilGBloecke +
            teilHBloecke
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
