package app.schwindeljournal.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import app.schwindeljournal.data.model.BuchTeil
import app.schwindeljournal.data.model.Modus
import app.schwindeljournal.data.model.Sprache
import app.schwindeljournal.data.model.VariantenTiefe

/**
 * Wissens-Bibliothek, aus dem Buchmanuskript abgeleitet (datenmodell-und-content-
 * mapping.md Abschnitt 1/3). Statischer, mit der App ausgelieferter Inhalt - wird bei
 * jedem App-Start aus [app.schwindeljournal.data.seed.ContentSeed] neu eingespielt
 * (OnConflictStrategy.REPLACE), damit redaktionelle Textaenderungen ueber App-Updates
 * ankommen, ohne dass jede Textaenderung eine neue Room-Migration braeuchte.
 */
@Entity(tableName = "content_block")
data class ContentBlockEntity(
    @PrimaryKey
    val id: String,
    val buchTeil: BuchTeil,
    val titel: String,
    val variantenTiefe: VariantenTiefe,
    val inhaltMarkdown: String,
    val sichtbarInModus: Set<Modus>,
    // true nur fuer den Teil-A-Warnzeichen-Block: identischer Wortlaut in allen drei
    // Modi, Sicherheitsinformationen werden nie gekuerzt (siehe CLAUDE.md).
    val istWarnzeichenInhalt: Boolean = false,
    // Mehrsprachigkeit (Phase 5): jede Sprache ihre eigenen Zeilen, id-Suffix pro
    // Sprache noetig (gleiche Regel wie VOLL/PEER, siehe PROJECT_LOG adversarial
    // gefundener Bug). Default DE, da DE die bestehenden ~70 Zeilen ohne Aenderung bleiben.
    val sprache: Sprache = Sprache.DE,
)
