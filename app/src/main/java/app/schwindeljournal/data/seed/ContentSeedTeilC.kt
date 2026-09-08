package app.schwindeljournal.data.seed

import app.schwindeljournal.data.local.entity.ContentBlockEntity
import app.schwindeljournal.data.model.BuchTeil
import app.schwindeljournal.data.model.Modus
import app.schwindeljournal.data.model.VariantenTiefe

// WICHTIG: id ist Room-Primary-Key. VOLL/PEER brauchen daher unterschiedliche ids
// (Suffix -peer) - sonst ueberschreibt die REPLACE-Seed-Strategie die VOLL-Zeile beim
// Einspielen der PEER-Zeile (adversarial gefunden, siehe PROJECT_LOG.md).
private fun ursache(
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
            inhaltMarkdown = voll.trimIndent(),
        ),
        ContentBlockEntity(
            id = "$id-peer",
            buchTeil = BuchTeil.C,
            titel = titel,
            variantenTiefe = VariantenTiefe.PEER,
            sichtbarInModus = setOf(Modus.PEER),
            inhaltMarkdown = peer.trimIndent(),
        ),
    )

internal val teilCBloecke: List<ContentBlockEntity> =
    ursache(
        id = "teilC-ursache-lagerungsschwindel",
        titel = "Lagerungsschwindel (BPPV)",
        voll =
            """
            Kleine Kalkkristalle in deinem Innenohr, die normalerweise fest sitzen, können sich lösen und in die Bogengänge geraten. Bewegst du deinen Kopf – etwa beim Umdrehen im Bett oder beim Zurückbeugen – "schwappen" diese Kristalle und melden deinem Gehirn eine Bewegung, die gar nicht stattfindet. Typisch: kurzer, aber heftiger Drehschwindel von wenigen Sekunden bis maximal einer Minute, ausgelöst durch eine bestimmte Kopfposition, danach klingt er von selbst ab.
            """,
        peer =
            """
            Kennst du das – du drehst dich im Bett um, und für ein paar Sekunden dreht sich alles heftig? Dahinter können kleine, verrutschte Kalkkristalle in deinem Innenohr stecken. Das Tröstliche: Es klingt meist von selbst innerhalb einer Minute wieder ab und ist gut behandelbar.
            """,
    ) +
        ursache(
            id = "teilC-ursache-meniere",
            titel = "Morbus Menière",
            voll =
                """
                Hier verändert sich der Druck der Flüssigkeit im Innenohr. Typisch beschrieben werden Anfälle von Minuten bis mehreren Stunden, oft begleitet von Ohrensausen, einem Druckgefühl im Ohr und vorübergehendem Hörverlust auf einer Seite.
                """,
            peer =
                """
                Bei Morbus Menière verändert sich der Druck der Flüssigkeit im Innenohr. Typisch sind Anfälle, die Minuten bis Stunden dauern können, oft zusammen mit Ohrensausen und einem Druckgefühl im Ohr. Wenn dir das bekannt vorkommt, ist das ein guter Punkt fürs HNO-Gespräch.
                """,
        ) +
        ursache(
            id = "teilC-ursache-neuritis-vestibularis",
            titel = "Neuritis vestibularis (Vestibularisausfall)",
            voll =
                """
                Eine (vermutlich meist virale) Entzündung des Gleichgewichtsnervs. Typisch: ein plötzlicher, sehr starker Dauerschwindel über Tage, oft mit Übelkeit und Erbrechen, der sich anders als BPPV nicht durch eine bestimmte Kopfposition auslösen lässt, sondern durchgehend besteht und sich erst über Tage bis Wochen bessert.
                """,
            peer =
                """
                Ein plötzlicher, sehr starker Dauerschwindel über mehrere Tage, oft mit Übelkeit – anders als beim Lagerungsschwindel lässt er sich nicht durch eine bestimmte Kopfposition auslösen, er ist einfach da. Dahinter kann eine Entzündung des Gleichgewichtsnervs stecken. Das klingt beängstigend, bessert sich aber meist über Tage bis Wochen.
                """,
        ) +
        ursache(
            id = "teilC-ursache-migraene",
            titel = "Migräne-assoziierter Schwindel",
            voll =
                """
                Nicht jede Migräne zeigt sich als klassischer Kopfschmerz – bei manchen Menschen dominiert Schwindel, teils sogar ganz ohne Kopfschmerz. Typisch: episodenhaftes Auftreten, oft mit Licht- oder Geräuschempfindlichkeit, manchmal mit einer "Aura" (Sehörungen) davor.
                """,
            peer =
                """
                Nicht jede Migräne zeigt sich als Kopfschmerz – bei manchen dominiert Schwindel, sogar ganz ohne Kopfschmerz. Wenn dein Schwindel episodenhaft auftritt und du zusätzlich licht- oder geräuschempfindlich bist, könnte das ein Anhaltspunkt sein.
                """,
        ) +
        ursache(
            id = "teilC-ursache-kreislauf",
            titel = "Kreislauf, Blutzucker, Flüssigkeitshaushalt",
            voll =
                """
                Ein zu niedriger Blutdruck beim Aufstehen (orthostatische Hypotonie), zu wenig getrunken, zu lange nichts gegessen – all das kann sich als Benommenheit, Schwarzwerden vor Augen oder "weiche Knie" äußern. Meist kurz, oft reproduzierbar (z. B. immer beim schnellen Aufstehen), und genau deshalb ein gutes Beispiel dafür, wie wertvoll die Steckbrief-Angabe zum Blutdruck sein kann.
                """,
            peer =
                """
                Schnell aufgestanden und kurz schwarz vor Augen geworden? Das kennen viele. Zu wenig getrunken, zu lange nichts gegessen, oder einfach ein kurzer Blutdruckabfall – das äußert sich meist als Benommenheit, weniger als klassischer Drehschwindel. Trag deinen Blutdruck in deinem Steckbrief ein, das hilft beim Einordnen.
                """,
        ) +
        ursache(
            id = "teilC-ursache-medikamente",
            titel = "Medikamentennebenwirkungen",
            voll =
                """
                Blutdrucksenker, bestimmte Antibiotika, Beruhigungsmittel, Schmerzmittel und viele weitere Wirkstoffgruppen können Schwindel als Nebenwirkung haben – einzeln oder erst recht in Kombination mehrerer Präparate. Deshalb die Bitte aus deinem Steckbrief: Trage wirklich jedes Medikament ein, auch rezeptfreie.
                """,
            peer =
                """
                Das hätte ich nicht gedacht, bevor ich mich damit beschäftigt habe: Richtig viele Medikamente können Schwindel als Nebenwirkung haben – Blutdrucksenker, Schlafmittel, Schmerzmittel, manche Antibiotika, und erst recht in Kombination. Deshalb der Tipp aus deinem Steckbrief: trag wirklich jedes Medikament ein, auch rezeptfreie.
                """,
        ) +
        ursache(
            id = "teilC-ursache-angst-stress",
            titel = "Angst- und stressbedingter Schwindel",
            voll =
                """
                Anhaltende Anspannung, Sorgen oder eine akute Angstreaktion können über eine veränderte Atmung (oft flacher und schneller, manchmal Richtung Hyperventilation) zu Benommenheit führen. Das fühlt sich für Betroffene oft genauso "echt" an wie ein körperlich verursachter Schwindel – weil es das auch ist, nur mit einem anderen Auslöser.
                """,
            peer =
                """
                Anhaltender Stress oder eine akute Angstreaktion können sich über eine veränderte Atmung als Schwindel oder Benommenheit äußern. Das ist kein "eingebildeter" Schwindel – es ist eine echte körperliche Reaktion, genauso ernst zu nehmen wie jede andere Ursache in diesem Buch.
                """,
        ) +
        listOf(
            ContentBlockEntity(
                id = "teilC-tabelle",
                buchTeil = BuchTeil.C,
                titel = "Orientierungstabelle",
                variantenTiefe = VariantenTiefe.VOLL,
                sichtbarInModus = setOf(Modus.KOMPASS),
                inhaltMarkdown =
                    """
                    Eine erste Orientierung nach typischer Dauer und Auslöser – bewusst keine Checkliste zum Selbstdiagnostizieren, die Übergänge sind in der Praxis oft fließend, und mehrere Ursachen können gleichzeitig vorliegen.

                    - Lagerungsschwindel (BPPV): Sekunden bis ca. 1 Minute, ausgelöst durch bestimmte Kopfbewegung
                    - Morbus Menière: Minuten bis Stunden, oft ohne erkennbaren Auslöser
                    - Neuritis vestibularis: Tage (Dauerzustand), kein Bewegungsauslöser nötig
                    - Migräne-assoziiert: Minuten bis Stunden, episodenhaft, migräne-typische Trigger
                    - Kreislauf/Blutdruck: Sekunden bis wenige Minuten, Aufstehen, Hitze, wenig getrunken
                    - Medikamentennebenwirkung: variabel, oft dauerhaft solange eingenommen, nach Einnahme/neuem Präparat
                    - Angst-/stressbedingt: Minuten, situationsabhängig, Stresssituation/Anspannung
                    - Zervikogen (Teil B): variabel, oft Minuten, Kopf-/Nackenbewegung, Verspannung
                    """.trimIndent(),
            ),
            ContentBlockEntity(
                id = "teilC-tabelle-peer",
                buchTeil = BuchTeil.C,
                titel = "Orientierungstabelle",
                variantenTiefe = VariantenTiefe.PEER,
                sichtbarInModus = setOf(Modus.PEER),
                inhaltMarkdown =
                    """
                    Ich hab mir irgendwann selbst so eine Tabelle gebastelt, um den Überblick zu behalten – Dauer und Auslöser sind oft der beste erste Hinweis, welche Ursachen überhaupt infrage kommen. Sieh sie dir als groben Kompass an, nicht als Diagnosewerkzeug.
                    """.trimIndent(),
            ),
        )
