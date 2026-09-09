package app.schwindeljournal.data.seed

import app.schwindeljournal.data.local.entity.ContentBlockEntity
import app.schwindeljournal.data.model.BuchTeil
import app.schwindeljournal.data.model.Modus
import app.schwindeljournal.data.model.Sprache
import app.schwindeljournal.data.model.VariantenTiefe

internal val teilGBloeckeEn: List<ContentBlockEntity> =
    listOf(
        ContentBlockEntity(
            id = "teilG-geschichte-autor-en",
            buchTeil = BuchTeil.G,
            titel = "My own story",
            variantenTiefe = VariantenTiefe.VOLL,
            sichtbarInModus = setOf(Modus.KOMPASS, Modus.PEER),
            sprache = Sprache.EN,
            inhaltMarkdown =
                """
                I want to be open here, because it's exactly what I would have wished for from others affected: for me, the dizziness came suddenly – even while sitting, even in the car, without me having moved at all. The first doctor's visit didn't bring any clarity. What stood out: at that time I was very tense in my shoulder and neck area. I then saw a chiropractor, who worked on several spots along the spine – after that, the dizziness was gone. For the tension itself, I then had to do exercises regularly afterward; it wasn't a single appointment that solved everything.

                One reason for this, which I only really understood afterward: I have mild scoliosis. If you don't use the supporting muscles regularly, my spine pulls more strongly to one side – that creates tension, the tension makes movement more unpleasant, so you move even less, and the tension increases further. A real vicious circle, which I only got out of through regular, conscious movement.

                I'm not telling you this so you conclude "the chiropractor will fix it" – for me it was this combination of targeted treatment and then working on the tension myself. For you, the cause might be something completely different. But what I want to leave you with: an inconclusive first doctor's visit is not the end of the road, and looking at tension and the spine turned out, for me personally, to be the decisive clue.
                """.trimIndent(),
        ),
    )
