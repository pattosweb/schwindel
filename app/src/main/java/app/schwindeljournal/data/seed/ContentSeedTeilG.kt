package app.schwindeljournal.data.seed

import app.schwindeljournal.data.local.entity.ContentBlockEntity
import app.schwindeljournal.data.model.BuchTeil
import app.schwindeljournal.data.model.Modus
import app.schwindeljournal.data.model.VariantenTiefe

/**
 * VOLL und PEER sind hier identisch (Text ist im Manuskript bereits im Peer-Ton
 * geschrieben, siehe content-varianten-texte.md-Hinweis) - eine Zeile deckt beide
 * Modi ab statt inhaltsgleiche Duplikate anzulegen.
 */
internal val teilGBloecke: List<ContentBlockEntity> =
    listOf(
        ContentBlockEntity(
            id = "teilG-geschichte-autor",
            buchTeil = BuchTeil.G,
            titel = "Meine eigene Geschichte",
            variantenTiefe = VariantenTiefe.VOLL,
            sichtbarInModus = setOf(Modus.KOMPASS, Modus.PEER),
            inhaltMarkdown =
                """
                Ich will an dieser Stelle offen sein, weil es genau das ist, was ich mir von anderen Betroffenen auch gewünscht hätte: Bei mir kam der Schwindel plötzlich – auch im Sitzen, sogar im Auto, ohne dass ich mich bewegt hätte. Der erste Arztbesuch brachte keine Klärung. Was auffiel: Ich war zu dieser Zeit sehr verspannt im Schulter- und Nackenbereich. Ich habe daraufhin einen Chiropraktiker aufgesucht, der mehrere Stellen im Bereich der Wirbelsäule bearbeitet hat – danach war der Schwindel weg. Für die Verspannungen selbst musste ich anschließend regelmäßig Übungen machen, das war kein einmaliger Termin, der alles gelöst hat.

                Ein Grund dafür, den ich erst im Nachhinein richtig verstanden habe: Ich habe eine leichte Skoliose. Nutzt man die stützende Muskulatur nicht regelmäßig, zieht sich die Wirbelsäule bei mir stärker zu einer Seite – das erzeugt Verspannung, die Verspannung macht Bewegung unangenehmer, dadurch bewegt man sich noch weniger, und die Verspannung nimmt weiter zu. Ein echter Teufelskreis, aus dem ich erst durch regelmäßige, bewusste Bewegung wieder herausgekommen bin.

                Ich erzähle das nicht, damit du daraus ableitest, "der Chiropraktiker wird es schon richten" – bei mir war es diese Kombination aus gezielter Behandlung und anschließender Eigenarbeit an der Verspannung. Bei dir kann die Ursache eine ganz andere sein. Was ich dir aber mitgeben will: Ein unauffälliger erster Arztbesuch ist nicht das Ende des Weges, und der Blick auf Verspannung und Wirbelsäule hat sich bei mir persönlich als der entscheidende Hinweis erwiesen.
                """.trimIndent(),
        ),
    )
