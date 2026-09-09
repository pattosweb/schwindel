@file:Suppress("MagicNumber")
// Reine Layout-/Koordinaten-Datei: die Zahlen sind Schriftgroessen, Zeilenabstaende
// und Text-Baseline-Offsets in Punkten - fuer jede einzelne einen Namen zu vergeben
// ("textBaselineOffsetZehn" o. Ae.) waere weniger lesbar als die Zahl direkt an der
// drawText-Zeile, in der sie verwendet wird.

package app.schwindeljournal.pdf

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.pdf.PdfDocument
import android.text.TextPaint
import android.text.TextUtils
import app.schwindeljournal.data.local.entity.AnsprechpartnerEntity
import app.schwindeljournal.data.local.entity.JournalEntryEntity
import app.schwindeljournal.data.local.entity.MedikamentEntity
import app.schwindeljournal.data.local.entity.SymptomEntity
import app.schwindeljournal.data.local.entity.UserProfileEntity
import app.schwindeljournal.data.model.Ampel
import app.schwindeljournal.data.model.anzeigename
import app.schwindeljournal.ui.components.formatiereDatum
import app.schwindeljournal.ui.components.formatiereUhrzeit
import app.schwindeljournal.ui.journalverlauf.MusterHinweis
import app.schwindeljournal.ui.journalverlauf.renderDe

private const val SEITE_BREITE = 595
private const val SEITE_HOEHE = 842
private const val RAND = 40f
private const val ZEILE_HOEHE = 16f

/**
 * Minimalistischer, textbasierter PDF-Zeichner auf android.graphics.pdf.PdfDocument
 * (Bordmittel, keine Layout-Engine). lc-debt: Tabellenzellen werden pro Zeile
 * abgeschnitten statt umgebrochen; Upgrade-Pfad bei Bedarf: Paint.breakText pro
 * Zelle statt TextUtils.ellipsize.
 */
class PdfZeichner(
    private val document: PdfDocument,
) {
    private var seitenNummer = 0
    private var seite: PdfDocument.Page? = null
    private var canvas: Canvas? = null
    private var y = 0f

    private val titelPaint =
        TextPaint().apply {
            textSize = 18f
            isFakeBoldText = true
            color = Color.BLACK
        }
    private val ueberschriftPaint =
        TextPaint().apply {
            textSize = 13f
            isFakeBoldText = true
            color = Color.BLACK
        }
    private val textPaint =
        TextPaint().apply {
            textSize = 10f
            color = Color.BLACK
        }
    private val kleinPaint =
        TextPaint().apply {
            textSize = 8f
            color = Color.DKGRAY
        }

    fun neueSeite() {
        seite?.let { document.finishPage(it) }
        seitenNummer++
        val info = PdfDocument.PageInfo.Builder(SEITE_BREITE, SEITE_HOEHE, seitenNummer).create()
        val neueSeite = document.startPage(info)
        seite = neueSeite
        canvas = neueSeite.canvas
        y = RAND
    }

    fun fertigstellen() {
        seite?.let { document.finishPage(it) }
    }

    fun titel(text: String) {
        sicherstellenPlatz(24f)
        canvas?.drawText(text, RAND, y + 18f, titelPaint)
        y += 28f
    }

    fun ueberschrift(text: String) {
        sicherstellenPlatz(20f)
        canvas?.drawText(text, RAND, y + 12f, ueberschriftPaint)
        y += 20f
    }

    fun absatz(text: String) {
        umbrechen(text, textPaint, SEITE_BREITE - 2 * RAND).forEach { zeile ->
            sicherstellenPlatz(ZEILE_HOEHE)
            canvas?.drawText(zeile, RAND, y + 10f, textPaint)
            y += ZEILE_HOEHE
        }
    }

    fun kleingedrucktes(text: String) {
        sicherstellenPlatz(ZEILE_HOEHE)
        canvas?.drawText(text, RAND, y + 8f, kleinPaint)
        y += ZEILE_HOEHE
    }

    fun leerzeile(hoehe: Float = 8f) {
        y += hoehe
    }

    fun tabellenZeile(
        spalten: List<String>,
        breiten: List<Float>,
        fett: Boolean = false,
    ) {
        sicherstellenPlatz(ZEILE_HOEHE)
        var x = RAND
        val paint = textPaint
        val alterStil = paint.isFakeBoldText
        paint.isFakeBoldText = fett
        spalten.forEachIndexed { i, text ->
            val breite = breiten.getOrElse(i) { 80f }
            val gekuerzt = TextUtils.ellipsize(text, paint, breite - 4f, TextUtils.TruncateAt.END).toString()
            canvas?.drawText(gekuerzt, x, y + 10f, paint)
            x += breite
        }
        paint.isFakeBoldText = alterStil
        y += ZEILE_HOEHE
    }

    private fun sicherstellenPlatz(benoetigt: Float) {
        if (y + benoetigt > SEITE_HOEHE - RAND) {
            neueSeite()
        }
    }

    private fun umbrechen(
        text: String,
        paint: Paint,
        maxBreite: Float,
    ): List<String> {
        val zeilen = mutableListOf<String>()
        var aktuelle = StringBuilder()
        text.split(" ").forEach { wort ->
            val kandidat = if (aktuelle.isEmpty()) wort else "$aktuelle $wort"
            if (paint.measureText(kandidat) > maxBreite && aktuelle.isNotEmpty()) {
                zeilen.add(aktuelle.toString())
                aktuelle = StringBuilder(wort)
            } else {
                aktuelle = StringBuilder(kandidat)
            }
        }
        if (aktuelle.isNotEmpty()) zeilen.add(aktuelle.toString())
        return zeilen
    }
}

fun PdfZeichner.steckbriefSeite(
    profil: UserProfileEntity,
    medikamente: List<MedikamentEntity>,
    ansprechpartner: List<AnsprechpartnerEntity>,
) {
    titel("Schwindeljournal – Steckbrief")
    kleingedrucktes("Dieses Dokument ersetzt keine ärztliche Untersuchung.")
    leerzeile(12f)

    ueberschrift("Zur Person")
    absatz("Geburtsjahr: ${profil.geburtsjahr ?: "–"}")
    absatz("Beruf mit Bildschirmarbeit/einseitiger Haltung: ${jaNeinText(profil.berufMitBelastung)}")
    leerzeile()

    ueberschrift("Medikamente")
    if (medikamente.isEmpty()) {
        absatz("Keine eingetragen.")
    } else {
        medikamente.forEach { m ->
            val zusatz =
                listOfNotNull(
                    m.dosierung,
                    m.seitWann?.let { "seit ${formatiereDatum(it)}" },
                ).joinToString(", ")
            absatz("• ${m.name}" + if (zusatz.isNotBlank()) " ($zusatz)" else "")
        }
    }
    leerzeile()

    ueberschrift("Vorerkrankungen / frühere Verletzungen")
    absatz(profil.vorerkrankungen?.ifBlank { null } ?: "Keine Angabe.")
    absatz(profil.fruehereVerletzungenKopfNacken?.ifBlank { null } ?: "Keine Angabe.")
    leerzeile()

    ueberschrift("Verlauf")
    absatz("Erster Schwindel-Vorfall: ${jaNeinText(profil.ersterVorfall)}")
    profil.seitWannWiederkehrend?.ifBlank { null }?.let { absatz("Wiederkehrend seit: $it") }
    leerzeile()

    ueberschrift("Blutdruck")
    val blutdruckZeile =
        listOfNotNull(
            profil.letzterBlutdruck?.ifBlank { null },
            profil.letzterBlutdruckDatum?.let { "gemessen am ${formatiereDatum(it)}" },
        ).joinToString(", ")
    absatz(blutdruckZeile.ifBlank { "Keine Angabe." })
    leerzeile()

    ueberschrift("Ansprechpartner")
    if (ansprechpartner.isEmpty()) {
        absatz("Keine eingetragen.")
    } else {
        ansprechpartner.forEach { a ->
            val zusatz = listOfNotNull(a.name, a.telefon).joinToString(", ")
            absatz("• ${a.rolle}" + if (zusatz.isNotBlank()) " ($zusatz)" else "")
        }
    }
}

private fun jaNeinText(wert: Boolean?): String =
    when (wert) {
        true -> "Ja"
        false -> "Nein"
        null -> "Keine Angabe"
    }

private val TABELLEN_SPALTEN_BREITEN = listOf(65f, 45f, 80f, 40f, 130f, 105f, 50f)

fun PdfZeichner.journalTabelle(
    eintraege: List<JournalEntryEntity>,
    symptomeProEintrag: Map<Long, List<SymptomEntity>>,
) {
    titel("Journal-Verlauf")
    if (eintraege.isEmpty()) {
        absatz("Noch keine Einträge vorhanden.")
        return
    }
    tabellenZeile(
        listOf("Datum", "Uhrzeit", "Ampel", "Dauer", "Situation", "Begleitsymptome", "Warnz."),
        TABELLEN_SPALTEN_BREITEN,
        fett = true,
    )
    eintraege.sortedWith(compareBy({ it.datum }, { it.uhrzeit })).forEach { eintrag ->
        val symptome = symptomeProEintrag[eintrag.id]?.joinToString(", ") { it.typ.anzeigename() }.orEmpty()
        tabellenZeile(
            listOf(
                formatiereDatum(eintrag.datum),
                formatiereUhrzeit(eintrag.uhrzeit),
                eintrag.ampel.anzeigename(),
                eintrag.dauerSekunden?.let { "${it / 60} Min" } ?: "–",
                eintrag.situation.orEmpty(),
                symptome,
                if (eintrag.warnzeichenKeinesAufgetreten) "–" else "JA",
            ),
            TABELLEN_SPALTEN_BREITEN,
        )
    }
}

fun PdfZeichner.zusammenfassung(
    eintraege: List<JournalEntryEntity>,
    musterHinweise: List<MusterHinweis>,
) {
    titel("Zusammenfassung")

    ueberschrift("Anzahl nach Ampelfarbe")
    Ampel.entries.forEach { ampel ->
        val anzahl = eintraege.count { it.ampel == ampel }
        absatz("${ampel.anzeigename()}: $anzahl")
    }
    leerzeile()

    ueberschrift("Mögliche Muster (keine Diagnose)")
    if (musterHinweise.isEmpty()) {
        absatz("Noch nicht genug Einträge für Muster-Hinweise.")
    } else {
        musterHinweise.forEach { absatz("• ${it.renderDe()}") }
    }
    leerzeile()
    kleingedrucktes(
        "Diese Hinweise sind reine Häufigkeitsbeobachtungen aus deinen Einträgen, keine " +
            "Diagnose. Bitte in jedem Fall ärztlich abklären lassen.",
    )
}
