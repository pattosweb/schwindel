package app.schwindeljournal.pdf

import android.content.Context
import android.graphics.pdf.PdfDocument
import android.net.Uri
import androidx.core.content.FileProvider
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.time.format.DateTimeFormatter
import javax.inject.Inject

/**
 * PDF-Export fuers Arztgespraech (Buch Teil F: "Falls vorhanden: den PDF-Export aus
 * der Schwindeljournal-App"). Bewusst mit Android-Bordmittel (PdfDocument, keine
 * externe Bibliothek) - siehe Skill Android-App-Architektur "Erst Bordmittel, dann
 * Eigenbau". Inhaltlich immer vollstaendig, unabhaengig vom aktuellen Modus
 * (Sicherheitsrelevanz, siehe CLAUDE.md/datenmodell-und-content-mapping.md Abschnitt 2).
 */
class JournalPdfExporter
    @Inject
    constructor(
        @ApplicationContext private val context: Context,
    ) {
        suspend fun exportiere(daten: JournalExportDaten): Uri =
            withContext(Dispatchers.IO) {
                val symptomeProEintrag = daten.symptome.groupBy { it.entryId }
                val document = PdfDocument()
                val zeichner = PdfZeichner(document)

                zeichner.neueSeite()
                zeichner.steckbriefSeite(daten.profil, daten.medikamente, daten.ansprechpartner)

                zeichner.neueSeite()
                zeichner.journalTabelle(daten.eintraege, symptomeProEintrag)

                zeichner.neueSeite()
                zeichner.zusammenfassung(daten.eintraege, daten.musterHinweise)

                zeichner.fertigstellen()

                val ordner = File(context.cacheDir, "exports").apply { mkdirs() }
                val zeitstempel = DateTimeFormatter.ofPattern("yyyyMMdd_HHmm").format(java.time.LocalDateTime.now())
                val datei = File(ordner, "schwindeljournal_$zeitstempel.pdf")
                FileOutputStream(datei).use { document.writeTo(it) }
                document.close()

                FileProvider.getUriForFile(context, "${context.packageName}.fileprovider", datei)
            }
    }
