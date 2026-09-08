package app.schwindeljournal.data.seed

import app.schwindeljournal.data.local.entity.ContentBlockEntity
import app.schwindeljournal.data.model.BuchTeil
import app.schwindeljournal.data.model.Modus
import app.schwindeljournal.data.model.VariantenTiefe

internal val teilBBloecke: List<ContentBlockEntity> =
    listOf(
        ContentBlockEntity(
            id = "teilB-atlas",
            buchTeil = BuchTeil.B,
            titel = "Der Atlas und die Propriozeption",
            variantenTiefe = VariantenTiefe.VOLL,
            sichtbarInModus = setOf(Modus.KOMPASS),
            inhaltMarkdown =
                """
                Stell dir deine Halswirbelsäule als eine Kette von sieben Bausteinen vor, die deinen Kopf tragen und gleichzeitig beweglich halten. Der oberste dieser Bausteine heißt Atlas (in der Fachsprache C1) – benannt nach der griechischen Sagengestalt, die das Himmelsgewölbe trägt. Kein Zufall: Genau dieser Wirbel trägt das Gewicht deines Kopfes und ermöglicht gleichzeitig einen Großteil der Kopfdrehung.

                Was die meisten Menschen nicht wissen: In diesem Bereich sitzen extrem viele Nervenzellen, die deinem Gehirn ständig melden, wie dein Kopf im Raum steht – man nennt das Propriozeption. Diese Meldungen laufen in denselben Hirnarealen zusammen, die auch die Signale aus deinem Innenohr (deinem eigentlichen Gleichgewichtsorgan) und aus deinen Augen verarbeiten. Dein Gehirn gleicht diese drei Informationsquellen ständig miteinander ab. Wenn eine davon "falsche" oder widersprüchliche Signale sendet – zum Beispiel durch eine Verspannung oder Fehlstellung im oberen Nackenbereich – kann daraus ein Schwindelgefühl entstehen, obwohl dein Innenohr völlig gesund ist.
                """.trimIndent(),
        ),
        ContentBlockEntity(
            id = "teilB-atlas-peer",
            buchTeil = BuchTeil.B,
            titel = "Der Atlas und die Propriozeption",
            variantenTiefe = VariantenTiefe.PEER,
            sichtbarInModus = setOf(Modus.PEER),
            inhaltMarkdown =
                """
                Das hat mich wirklich überrascht, als ich es zum ersten Mal gehört habe: Der oberste Wirbel deiner Halswirbelsäule, der Atlas, trägt nicht nur deinen Kopf – er meldet deinem Gehirn ständig, wie dein Kopf im Raum steht. Diese Meldungen landen im selben Bereich deines Gehirns wie die Signale aus deinem Innenohr und deinen Augen. Wenn diese drei Quellen sich nicht einig sind – zum Beispiel weil dein Nacken verspannt ist – kann daraus Schwindel entstehen, obwohl mit deinem Innenohr alles in Ordnung ist.
                """.trimIndent(),
        ),
        ContentBlockEntity(
            id = "teilB-zervikogen",
            buchTeil = BuchTeil.B,
            titel = "Zervikogener Schwindel",
            variantenTiefe = VariantenTiefe.VOLL,
            sichtbarInModus = setOf(Modus.KOMPASS),
            inhaltMarkdown =
                """
                Für diese Art von Schwindel, deren Ursprung im Bereich der Halswirbelsäule vermutet wird, gibt es einen Fachbegriff: zervikogener Schwindel ("zervikal" = die Halswirbelsäule betreffend). Wichtig zu wissen: Er ist in der Medizin nicht unumstritten – es gibt keinen einzelnen Test, der ihn zweifelsfrei nachweist. Genau deshalb lohnt sich für dich als Betroffenen die genaue Beobachtung, die du mit diesem Journal betreibst.

                Typische Beobachtungen, die *möglicherweise* auf eine Beteiligung der Halswirbelsäule hindeuten (ohne dass das eine Diagnose wäre):

                - Der Schwindel tritt eher bei oder kurz nach Kopf-/Nackenbewegungen auf (z. B. beim Zurückschauen, nach oben schauen, langem Sitzen am Bildschirm)
                - Er geht mit Nackenverspannung, Nackenschmerz oder einem Gefühl von "Steifigkeit" im Schulter-Nacken-Bereich einher
                - Er verstärkt sich nach schlechtem Schlaf oder ungewohnter Kissenhöhe
                - Kopfschmerzen, die vom Nacken in Richtung Schläfe/Stirn ziehen, treten begleitend auf

                Diese Punkte findest du bewusst auch als eigene Spalten in deinem Journal wieder – nicht, damit du dir selbst eine Diagnose stellst, sondern damit du diese Beobachtungen konkret und mit Beispielen in ein Arzt-, Physio- oder Chiropraktik-Gespräch mitnehmen kannst.

                ### Schleudertrauma und alte Verletzungen
                Auch länger zurückliegende Ereignisse können eine Rolle spielen – ein Auffahrunfall vor Jahren, ein Sturz, eine unbemerkte Fehlbelastung über lange Zeit (z. B. durch einseitige Körperhaltung im Beruf). Der Zusammenhang zwischen einem solchen Ereignis und heutigem Schwindel ist nicht immer offensichtlich, weil zwischen Ursache und Symptom oft viel Zeit liegt. Falls du in der Vergangenheit ein solches Ereignis hattest, notiere es dir einmal separat – es ist eine der ersten Fragen, die ein auf die Halswirbelsäule spezialisierter Therapeut dir stellen wird.
                """.trimIndent(),
        ),
        ContentBlockEntity(
            id = "teilB-zervikogen-peer",
            buchTeil = BuchTeil.B,
            titel = "Zervikogener Schwindel",
            variantenTiefe = VariantenTiefe.PEER,
            sichtbarInModus = setOf(Modus.PEER),
            inhaltMarkdown =
                """
                Für das, was ich erlebt habe, gibt es einen Namen: zervikogener Schwindel – Schwindel, dessen Ursprung im Nacken vermutet wird. Wichtig zu wissen: Selbst Ärzte sind sich da nicht immer einig, es gibt keinen eindeutigen Test dafür. Was mir geholfen hat: genau hinzuschauen. Kommt der Schwindel eher bei oder nach Kopfbewegung? Ist gleichzeitig der Nacken verspannt? Wird es nach schlechtem Schlaf schlimmer? Das sind keine Diagnosekriterien, aber gute Gesprächsanstöße für Arzt, Physio oder Chiropraktiker.
                """.trimIndent(),
        ),
        ContentBlockEntity(
            id = "teilB-skoliose",
            buchTeil = BuchTeil.B,
            titel = "Skoliose und der Teufelskreis der Verspannung",
            variantenTiefe = VariantenTiefe.VOLL,
            sichtbarInModus = setOf(Modus.KOMPASS),
            inhaltMarkdown =
                """
                Nicht nur der Atlas allein, auch die Statik der gesamten Wirbelsäule kann eine Rolle spielen. Bei einer Skoliose – einer seitlichen Verkrümmung der Wirbelsäule – reicht oft schon eine Phase mit wenig Bewegung, damit sich die stützende Muskulatur ungleich beansprucht und die Wirbelsäule sich stärker zu einer Seite zieht. Das erhöht die Verspannung, vor allem im Schulter-Nacken-Bereich, was wiederum das Bewegen unangenehmer macht – und genau das reduziert die Bewegung weiter. Es entsteht ein Teufelskreis aus Schonhaltung, mehr Verspannung, weniger Bewegung, noch mehr Verspannung. Da die obere Halswirbelsäule so eng mit deinem Gleichgewichtssystem verknüpft ist, kann sich eine solche chronische Verspannung durchaus auch auf Schwindel auswirken – nicht direkt durch die Skoliose selbst, sondern über den Umweg der Verspannung, die sie begünstigt. Wenn du von einer Skoliose weißt, lohnt es sich, das in deinem Steckbrief zu vermerken und beim Beobachten besonders auf den Zusammenhang zwischen Bewegungsphasen (viel/wenig) und Verspannungsgefühl zu achten.

                ### Was das für dein weiteres Vorgehen bedeutet
                Dieses Kapitel soll dich nicht davon überzeugen, dass "es der Nacken ist". Es soll dir einen zusätzlichen Blickwinkel geben, den viele Betroffene erst nach langer Ärzte-Odyssee zufällig entdecken. Nimm die Beobachtungspunkte aus diesem Kapitel mit in dein Journal – und in Teil F erfährst du, welcher Ansprechpartner für welche Beobachtung der passendere erste Weg sein kann.
                """.trimIndent(),
        ),
        ContentBlockEntity(
            id = "teilB-skoliose-peer",
            buchTeil = BuchTeil.B,
            titel = "Skoliose und der Teufelskreis der Verspannung",
            variantenTiefe = VariantenTiefe.PEER,
            sichtbarInModus = setOf(Modus.PEER),
            inhaltMarkdown =
                """
                Das ist bei mir persönlich der entscheidende Punkt gewesen: Ich habe eine leichte Skoliose. Wenn ich die Muskulatur nicht regelmäßig nutze, zieht sich meine Wirbelsäule stärker zu einer Seite – das erzeugt Verspannung, die Verspannung macht Bewegung unangenehmer, also bewege ich mich noch weniger, und die Verspannung nimmt weiter zu. Ein echter Teufelskreis. Falls du von einer Skoliose weißt: Beobachte mal bewusst, ob Bewegungsphasen und Verspannungsgefühl bei dir zusammenhängen.
                """.trimIndent(),
        ),
    )
