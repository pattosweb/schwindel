# Roadmap — Schwindeljournal-App

**Hinweis:** Diese Datei ist eine laufend gepflegte Arbeitshilfe, keine Wahrheitsquelle.
Bei Widerspruch zur Git-/Code-Historie gilt immer die Historie (siehe CLAUDE.md).
Technische Details zu jedem Punkt: siehe `datenmodell-und-content-mapping.md`.

## Phase 0 — Grundgerüst
- [ ] Android-Studio-Projekt anlegen (Kotlin + Compose)
- [ ] Gradle-Setup: Room, WorkManager, Compose-Charts-Library
- [ ] Grundlegende Navigation (Schnell-Erfassung / Journal-Verlauf / Wissens-Bibliothek /
      Übungs-Begleiter / Einstellungen)
- [ ] ktlint/detekt-Konfiguration, Test-Gate in CI (falls/sobald CI existiert)

## Phase 0.5 — Modus-Architektur (neu, vor Phase 1 nötig)
- [ ] `UserProfile.modus`-Feld + Enum (KOMPASS/PEER/QUICK) im Room-Schema
- [ ] Onboarding-Screen: Moduswahl mit kurzer Erklärung der drei Varianten
- [ ] Einstellungen: Moduswechsel jederzeit möglich, ohne Datenverlust
- [ ] UI-Grundgerüst so bauen, dass Sichtbarkeit von Feldern/Content vom aktuellen Modus
      abhängt (zentrale Modus-Abfrage, nicht verstreut in jedem Screen einzeln)

## Phase 1 — Kernfunktion: Erfassung
- [ ] Room-Datenmodell nach `datenmodell-und-content-mapping.md` Abschnitt 1
      (JournalEntry als Superset-Schema, alle modus-spezifischen Felder nullable)
- [ ] Schnell-Erfassung: modus-abhängige Feldmenge (Quick: Ampel + 1 Freitextzeile;
      Kompass/Peer: alle Felder aufklappbar), Ampel + optionale Sprachnotiz
- [ ] Detail-Nachtrag: Situation, Kopf-/Nackenposition, Begleitsymptome, Schlafqualität
      (nur Kompass/Peer sichtbar)
- [ ] Reflexionsfrage-Feld nur im Peer-Modus anzeigen
- [ ] Trigger-Tag-Bibliothek (vordefinierte, antippbare Situationstags)
- [ ] Warnzeichen-Checkbox ("keines der Warnzeichen aufgetreten") in allen Modi

## Phase 2 — Auswertung
- [ ] Zeitverlaufs-Diagramm (Ampelfarbe über Zeit) — in Quick-Modus vereinfachte Ansicht
- [ ] Korrelationsansicht (z. B. Schlafqualität ↔ Schwindelstärke) — nur Kompass/Peer,
      formuliert als "mögliches Muster", nie als Diagnosehinweis
- [ ] PDF-Export für Arztgespräch, Format an Buch-Teil F angelehnt, inhaltlich in allen
      Modi gleich vollständig (Sicherheitsrelevanz, siehe CLAUDE.md)

## Phase 3 — Wissens-Bibliothek & Übungen
- [ ] ContentBlock-Tabelle nach `datenmodell-und-content-mapping.md` Abschnitt 3 seeden
- [ ] Anzeige-Logik: Modus filtert `variantenTiefe` (KOMPASS→VOLL, PEER→PEER, QUICK→KURZ)
- [ ] Warnzeichen- und Glossar-Blocks immer sichtbar, unabhängig vom Modus
- [ ] Übungs-Begleiter mit Timer/Wiederholzähler (HWS-Mobilisation, vestibuläres
      Training) — Inhalte aus Teil E
- [x] PEER-/KURZ-Textfassungen der ContentBlocks — fertig, siehe
      `content-varianten-texte.md`

## Phase 4 — Komfort & Erinnerungen
- [ ] WorkManager-Erinnerung zum täglichen Nachtragen
- [ ] Einstellungen: Erinnerungszeit, Ampel-Symbolik anpassen (Barrierefreiheit)
- [ ] Reflexionsfragen-Pool für Peer-Modus hinterlegen (Inhalt noch zu definieren)

## Bewusst zurückgestellt (siehe PROJECT_LOG.md, Abschnitt 5)
- Cloud-Sync/Backup
- Wearable-Integration
- Mehrsprachigkeit
