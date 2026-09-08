# Datenmodell und Content-Mapping — Schwindeljournal-App

Technische Wahrheitsquelle für die Multi-Varianten-Architektur (siehe CLAUDE.md,
Abschnitt "Multi-Varianten-Architektur"). Vor jeder Schema-Änderung hier nachziehen.

---

## 1. Room-Entitäten (Vorschlag)

### `UserProfile` (aus der Steckbrief-Seite, Buch Abschnitt 4.1)
```
id: Long (PK)
modus: enum { KOMPASS, PEER, QUICK }   // aktuell gewählter Modus, jederzeit änderbar
geburtsjahr: Int?
berufMitBelastung: Boolean?
medikamente: List<Medikament>          // eigene Tabelle, siehe unten
vorerkrankungen: String?
frühereVerletzungenKopfNacken: String?
ersterVorfall: Boolean?
seitWannWiederkehrend: String?
letzterBlutdruck: String?
letzterBlutdruckDatum: LocalDate?
ansprechpartner: List<Ansprechpartner>  // eigene Tabelle, siehe unten
```

### `Medikament`
```
id: Long (PK)
profileId: Long (FK -> UserProfile)
name: String
dosierung: String?
seitWann: LocalDate?
```

### `Ansprechpartner`
```
id: Long (PK)
profileId: Long (FK -> UserProfile)
rolle: String        // "Hausarzt", "HNO", "Physio/Chiro", frei
name: String?
telefon: String?
```

### `JournalEntry` — Superset-Schema für alle drei Modi
Wichtig: **Ein** Schema, alle modus-spezifischen Felder nullable. Kein separates Schema
pro Modus, sonst bricht der modusübergreifende Verlauf.

```
id: Long (PK)
datum: LocalDate
uhrzeit: LocalTime                     // Kompass, Peer, Quick — Kernfeld
situation: String?                     // Kompass, Peer, Quick — Kernfeld (Quick: Freitext)
kopfNackenPosition: String?            // Kompass, Peer — optional/leer bei Quick
dauerSekunden: Int?                    // Kompass, Peer, Quick — Kernfeld
ampel: enum { GRUEN, GELB, ROT }       // Kompass, Peer, Quick — Kernfeld
begleitsymptome: List<Symptom>?        // Kompass, Peer — optional/leer bei Quick, eigene Tabelle
schlafqualitaetNachtDavor: String?     // Kompass, Peer — optional/leer bei Quick
kissenhoeheArt: String?                // Kompass, Peer — optional/leer bei Quick
tagesbewertung: String?                // Kompass, Peer, Quick — Kernfeld
reflexionsfrageAntwort: String?        // NUR Peer-Modus befüllt, sonst immer null
warnzeichenKeinesAufgetreten: Boolean  // alle Modi, Default true, Checkbox aus Teil A
```

### `Symptom` (Mehrfachauswahl-Tags für Begleitsymptome)
```
id: Long (PK)
entryId: Long (FK -> JournalEntry)
typ: enum { OHRENSAUSEN, NACKENSCHMERZ, KOPFSCHMERZ, SEHSTOERUNG, HERZKLOPFEN, SONSTIGES }
freitext: String?   // nur bei SONSTIGES
```

### `ContentBlock` — Wissens-Bibliothek, aus dem Buchmanuskript abgeleitet
```
id: String (PK, z. B. "teilA-vier-familien", "teilB-atlas", "teilC-tabelle")
buchTeil: enum { A, B, C, D, E, F, G, H }
titel: String
variantenTiefe: enum { VOLL, PEER, KURZ }   // eine Zeile pro Tiefe, nicht eine Spalte
inhaltMarkdown: String
sichtbarInModus: Set<Modus>                  // KOMPASS zeigt VOLL, PEER zeigt PEER, QUICK zeigt KURZ
istWarnzeichenInhalt: Boolean                 // true nur für den Teil-A-Warnzeichen-Block, immer in allen Modi sichtbar
```

**Wichtig zur Content-Pflege:** Pro Buch-Abschnitt existieren bis zu drei
`ContentBlock`-Zeilen (VOLL/PEER/KURZ), nicht ein Feld mit drei Unter-Texten — das
erlaubt es, einzelne Varianten unabhängig zu redigieren, ohne die anderen zu berühren.
Der Warnzeichen-Block (Teil A, Abschnitt "Wann Schwindel zum Notfall wird") ist die
einzige Ausnahme: Er wird in identischem Wortlaut in allen drei Modi angezeigt, da
Sicherheitsinformationen nicht "gekürzt" werden dürfen.

---

## 2. Modus-abhängige UI-Logik (Zusammenfassung für die Umsetzung)

| UI-Bereich | Kompass | Peer | Quick |
|---|---|---|---|
| Schnell-Erfassung | Ampel + alle Felder erreichbar (aufklappbar) | Ampel + Reflexionsfrage prominent | Nur Ampel + 1 Freitextzeile, 3-Tap-Ziel |
| Journal-Verlauf | Volle Tabelle | Volle Tabelle + Reflexionstext-Vorschau | Kompakte Liste, nur Datum + Ampel |
| Wissens-Bibliothek | Zeigt `ContentBlock` mit `VOLL` | Zeigt `ContentBlock` mit `PEER`, Teil G prominent oben | Zeigt nur Warnzeichen-Block + Kurzglossar (`KURZ`, Teil H) |
| Standard-Journal-Dauer | 60 Tage | 60 Tage | 30 Tage (in Einstellungen auf 60 erweiterbar) |
| Steckbrief | Vollständig | Vollständig | Vollständig (Sicherheitsrelevanz — hier keine Kürzung) |

**Grundsatz, der in CLAUDE.md ergänzt gehört:** Sicherheitsrelevante Inhalte
(Warnzeichen, Steckbrief-Pflichtfelder Medikamente/Vorerkrankungen) werden in **keinem**
Modus gekürzt oder versteckt — nur die vertiefenden Wissensinhalte und der
Journal-Komfort unterscheiden sich zwischen den Modi.

---

## 3. Vollständige Content-Mapping-Tabelle (Buchmanuskript → ContentBlock)

| Buch-Teil | Abschnitt im Manuskript | ContentBlock-ID (Vorschlag) | In Quick sichtbar? |
|---|---|---|---|
| A | Vertigo vs. Dizziness | `teilA-begriffe` | Nein |
| A | Vier Familien | `teilA-familien` | Nein |
| A | Warnzeichen | `teilA-warnzeichen` | **Ja, immer** |
| B | Atlas/HWS-Erklärung | `teilB-atlas` | Nein |
| B | Zervikogener Schwindel | `teilB-zervikogen` | Nein |
| B | Skoliose/Teufelskreis | `teilB-skoliose` | Nein |
| C | Ursachen-Einzeltexte | `teilC-ursache-<name>` (8 Blöcke) | Nein |
| C | Orientierungstabelle | `teilC-tabelle` | Nein |
| D | Schwacher/starker Eintrag | `teilD-beispiele` | Nein |
| D | Patient/Arzt-Checkliste | `teilD-checkliste` | Nein |
| E | Übungen (je Übung ein Block) | `teilE-uebung-<name>` (6 Blöcke) | Nein |
| F | Ansprechpartner-Tabelle | `teilF-tabelle` | Nein |
| F | Was mitnehmen | `teilF-mitnehmen` | Nein |
| G | Eigene Geschichte (Autor) | `teilG-geschichte-autor` | Nein |
| H | Glossar (je Begriff ein Eintrag) | `teilH-glossar-<begriff>` | **Ja, alle** |

Diese Tabelle ist die Arbeitsgrundlage für einen Seed-/Migrations-Datensatz
(`ContentBlock`-Inserts) — Claude Code kann daraus direkt die initiale Content-Befüllung
pro `variantenTiefe` ableiten, sobald die jeweiligen VOLL/PEER/KURZ-Texte aus dem
Manuskript vorliegen (aktuell liegt aus dem Manuskript primär die VOLL-Fassung vor; PEER-
und KURZ-Kürzungen sind ein eigener Redaktionsschritt, siehe offene Punkte in
PROJECT_LOG.md).

---

## 4. Was noch fehlt, bevor Claude Code die Content-Befüllung vollständig umsetzen kann

- PEER- und KURZ-Textfassungen der einzelnen ContentBlocks existieren noch nicht —
  aktuell nur VOLL (aus dem Manuskript). Vorschlag: Diese Kürzungen/Umtonungen als
  eigenen Redaktionsschritt vor oder parallel zur Implementierung erledigen, damit
  Claude Code nicht mit Platzhaltertexten arbeiten muss.
- Die Reflexionsfragen für den Peer-Modus (ein Feld pro Tag) sind inhaltlich noch nicht
  definiert — Vorschlag: 5–10 wiederkehrende Fragen als Pool (z. B. "Was hat dir heute
  gutgetan?", "Worüber machst du dir gerade Sorgen?"), täglich rotierend oder frei wählbar.
