package app.schwindeljournal.data.seed

import app.schwindeljournal.data.local.entity.ContentBlockEntity
import app.schwindeljournal.data.model.BuchTeil
import app.schwindeljournal.data.model.Modus
import app.schwindeljournal.data.model.VariantenTiefe

internal val teilABloecke: List<ContentBlockEntity> =
    listOf(
        ContentBlockEntity(
            id = "teilA-begriffe",
            buchTeil = BuchTeil.A,
            titel = "Vertigo vs. Dizziness",
            variantenTiefe = VariantenTiefe.VOLL,
            sichtbarInModus = setOf(Modus.KOMPASS),
            inhaltMarkdown =
                """
                Im Deutschen sagen wir zu fast allem "Schwindel" – ob sich der Raum dreht, der Boden wegkippt oder wir uns nur diffus "neben uns" fühlen. Im englischsprachigen Raum wird hier unterschieden, und diese Unterscheidung lohnt sich auch für dich: **Vertigo** meint die tatsächlich wahrgenommene Scheinbewegung – es dreht sich, kippt, schwankt. **Dizziness** meint eher Benommenheit, Leere im Kopf, ein Gefühl von "nicht ganz da sein", ohne dass sich etwas bewegt. Wenn du das für dich einordnen kannst, hilft das jedem Arzt enorm bei der Suche nach dem Grund – notiere dir daher in deinem Journal immer, welche der beiden Formen du erlebt hast.
                """.trimIndent(),
        ),
        ContentBlockEntity(
            id = "teilA-begriffe-peer",
            buchTeil = BuchTeil.A,
            titel = "Vertigo vs. Dizziness",
            variantenTiefe = VariantenTiefe.PEER,
            sichtbarInModus = setOf(Modus.PEER),
            inhaltMarkdown =
                """
                Weißt du noch, wie schwer es war, jemandem zu erklären, was du fühlst? Bei mir war genau das der Anfang. Ich habe gelernt, zwischen zwei Dingen zu unterscheiden: Dreht sich für dich tatsächlich der Raum, als würdest du dich drehen, obwohl du stillstehst? Das nennt man Vertigo. Oder ist es eher ein Gefühl von Watte im Kopf, ein "nicht ganz da sein", ohne dass sich etwas bewegt? Das ist eher Dizziness. Beides ist echt, beides zählt – aber wenn du das für dich unterscheiden kannst, hilft es jedem Arzt ungemein, dir wirklich zuzuhören.
                """.trimIndent(),
        ),
        ContentBlockEntity(
            id = "teilA-familien",
            buchTeil = BuchTeil.A,
            titel = "Die vier großen Familien",
            variantenTiefe = VariantenTiefe.VOLL,
            sichtbarInModus = setOf(Modus.KOMPASS),
            inhaltMarkdown =
                """
                Fast jeder Schwindel lässt sich grob einer von vier Gruppen zuordnen. Das ersetzt keine Diagnose, gibt dir aber eine erste Landkarte:

                ### 1. Vestibulärer Schwindel – das Innenohr
                Dein Innenohr enthält ein feines System aus Bogengängen und Sinnesorganen, das registriert, wie sich dein Kopf bewegt und wo "oben" ist. Ist dieses System gereizt oder gestört – etwa durch verrutschte Kalkkristalle (beim sogenannten Lagerungsschwindel), eine Entzündung des Gleichgewichtsnervs oder Druckveränderungen im Innenohr (wie bei Morbus Menière) – entsteht meist ein klarer Drehschwindel, oft an Kopfbewegungen gekoppelt.

                ### 2. Zervikogener Schwindel – die Halswirbelsäule
                Dein Nacken meldet deinem Gehirn ständig, wie dein Kopf im Raum steht. Verspannungen, Fehlhaltungen oder alte Verletzungen in diesem Bereich können diese Meldungen verfälschen und ein Schwindelgefühl auslösen, obwohl dein Innenohr gesund ist.

                ### 3. Kreislauf-/herzbedingter Schwindel
                Fällt dein Blutdruck kurzfristig ab – etwa beim schnellen Aufstehen, bei Flüssigkeitsmangel oder durch bestimmte Medikamente – bekommt dein Gehirn kurz zu wenig Durchblutung. Das äußert sich eher als Schwarzwerden vor Augen, Benommenheit (Dizziness) oder ein Gefühl von "Wegsacken", seltener als klassischer Drehschwindel. Auch Herzrhythmusstörungen können sich so äußern – deshalb gehört anhaltender oder wiederkehrender Schwindel dieser Art immer ärztlich abgeklärt.

                ### 4. Psychogener/stressbedingter Schwindel
                Anhaltender Stress, Angst oder Überforderung können sich körperlich als Schwindel oder Benommenheit äußern – oft in Kombination mit flacher Atmung oder Hyperventilation. Das ist kein "eingebildeter" Schwindel, sondern eine reale körperliche Reaktion auf psychische Belastung, und genauso ernst zu nehmen wie die anderen drei Familien.

                In der Praxis überschneiden sich diese Familien häufig – Stress kann einen bestehenden zervikogenen Schwindel verstärken, ein Innenohrproblem kann zusätzlich Angst und damit psychogene Anteile auslösen. Das genau ist der Grund, warum reines Abwarten oder Raten selten weiterhilft, und strukturierte Beobachtung – wie mit diesem Journal – den Unterschied macht.
                """.trimIndent(),
        ),
        ContentBlockEntity(
            id = "teilA-familien-peer",
            buchTeil = BuchTeil.A,
            titel = "Die vier großen Familien",
            variantenTiefe = VariantenTiefe.PEER,
            sichtbarInModus = setOf(Modus.PEER),
            inhaltMarkdown =
                """
                Am Anfang dachte ich, Schwindel ist einfach Schwindel. Mit der Zeit habe ich gelernt: Es gibt eigentlich vier ziemlich unterschiedliche "Familien". Manchmal steckt dein Innenohr dahinter, manchmal deine Halswirbelsäule, manchmal dein Kreislauf oder dein Herz, und manchmal ist es dein Nervensystem, das auf Stress reagiert. Das Fiese daran: Sie können sich überschneiden und sich gegenseitig verstärken. Genau deshalb bringt reines Rätselraten selten weiter – Beobachten schon.
                """.trimIndent(),
        ),
    )
