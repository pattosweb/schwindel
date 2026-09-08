# Roadmap — Schwindeljournal-App

**Hinweis:** Diese Datei ist eine laufend gepflegte Arbeitshilfe, keine Wahrheitsquelle.
Bei Widerspruch zur Git-/Code-Historie gilt immer die Historie (siehe CLAUDE.md).
Technische Details zu jedem Punkt: siehe `datenmodell-und-content-mapping.md`.

## Phase 0 — Grundgerüst
- [x] Android-Studio-Projekt anlegen (Kotlin + Compose) — `app.schwindeljournal`,
      minSdk 26, Version Catalog, Hilt, Gradle Wrapper 8.10.2
- [x] Gradle-Setup: Room — WorkManager und Compose-Charts-Library bewusst erst in
      Phase 4 bzw. Phase 2 hinzugefügt (YAGNI, keine ungenutzten Abhängigkeiten)
- [x] Grundlegende Navigation (Schnell-Erfassung / Journal-Verlauf / Wissens-Bibliothek /
      Übungs-Begleiter / Einstellungen) — alle fünf als Platzhalter-Screens, Bottom-Nav
- [x] ktlint/detekt-Konfiguration (Compose-Overrides in `config/detekt/detekt.yml`) —
      CI folgt, sobald eine CI-Umgebung existiert

## Phase 0.5 — Modus-Architektur (neu, vor Phase 1 nötig)
- [x] `UserProfile.modus`-Feld + Enum (KOMPASS/PEER/QUICK) im Room-Schema — Steckbrief-
      Cluster (UserProfile/Medikament/Ansprechpartner) v1, adversarial getestet
      (Doppel-Insert-Schutz, Persistenz nach simuliertem Prozess-Neustart)
- [x] Onboarding-Screen: Moduswahl mit kurzer Erklärung der drei Varianten
- [x] Einstellungen: Moduswechsel jederzeit möglich, ohne Datenverlust — manuell auf
      Emulator verifiziert (App-Kill + Neustart, Modus bleibt erhalten)
- [x] UI-Grundgerüst so bauen, dass Sichtbarkeit von Feldern/Content vom aktuellen Modus
      abhängt (zentrale Modus-Abfrage, nicht verstreut in jedem Screen einzeln) —
      `ModusViewModel` einmalig auf Activity-Ebene, explizit an Screens weitergereicht

## Phase 1 — Kernfunktion: Erfassung
- [x] Room-Datenmodell nach `datenmodell-und-content-mapping.md` Abschnitt 1
      (JournalEntry als Superset-Schema, alle modus-spezifischen Felder nullable) —
      additive Migration 1→2, adversarial getestet (MigrationTestHelper: bestehender
      Steckbrief bleibt beim Upgrade unangetastet)
- [x] Schnell-Erfassung: modus-abhängige Feldmenge (Quick: Ampel + 1 Freitextzeile;
      Kompass/Peer: alle Felder aufklappbar), Ampel + gleichwertige Sprachnotiz-Option
      auf jedem Freitextfeld (System-Diktier-Intent)
- [x] Detail-Nachtrag: Situation, Kopf-/Nackenposition, Begleitsymptome, Schlafqualität
      (nur Kompass/Peer sichtbar, aufklappbar ohne Animation)
- [x] Reflexionsfrage-Feld nur im Peer-Modus anzeigen (prominent, nicht im Detail-
      Bereich versteckt)
- [x] Trigger-Tag-Bibliothek (vordefinierte, antippbare Situationstags) — füllt die
      Situations-Freitextzeile, kein eigenes Datenfeld
- [x] Warnzeichen-Checkbox ("keines der Warnzeichen aufgetreten") in allen Modi,
      Default true (kein Zusatz-Tap im Normalfall)
- [x] (Vorgriff auf Phase 2) einfache chronologische Journal-Verlauf-Liste, damit der
      Erfassungs-Flow end-to-end nachvollziehbar ist — Diagramme/Auswertung bleiben
      Phase 2

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
