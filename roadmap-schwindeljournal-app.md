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

## Phase 0.5b — Steckbrief-UI (Nachtrag, Blueprint-Abgleich 08.09.2026)
- [x] Steckbrief-Screen (erreichbar über Einstellungen, in jedem Modus vollständig,
      wird nie gekürzt): Person (Geburtsjahr, Beruf/Belastung), Medikamente-Liste
      (sofort persistiert, unabhängig vom Profil-Speichern-Button), Vorerkrankungen,
      frühere Verletzungen, erster Vorfall + Seit-wann, letzter Blutdruck + Datum,
      Ansprechpartner-Liste (vordefinierte Rollen-Chips + freie Rolle)
- [x] Spracheingabe-Alternative auf jedem Freitextfeld auch hier konsequent umgesetzt
      (SprachEingabeTextField wiederverwendet)
- [x] Manuell auf Emulator verifiziert: Geburtsjahr/Medikament/Ansprechpartner anlegen →
      Speichern → App-Kill → Neustart → alles korrekt erhalten

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
- [x] Zeitverlaufs-Diagramm (Ampelfarbe über Zeit) — Balkenreihe ohne Animation/
      Interaktion, Legende mit Symbol+Text; Quick-Modus zeigt automatisch das
      vereinfachte 30-Tage-Fenster (in Einstellungen/Auswertung auf 60 Tage
      erweiterbar, additive Migration 2→3)
- [x] Korrelationsansicht ("Mögliche Muster") — nur Kompass/Peer, reine
      Haeufigkeitsauszaehlung aus strukturierten Feldern (Begleitsymptome +
      vordefinierte Trigger-Tags an Gelb-/Rot-Tagen), nie als Diagnose formuliert.
      Bewusst NICHT auf der freitextlichen Schlafqualitaet berechnet (Datentyp
      erlaubt keine belastbare Auszaehlung, siehe PROJECT_LOG "Bekannte Luecken")
- [x] PDF-Export für Arztgespräch (Bordmittel: android.graphics.pdf.PdfDocument),
      Steckbrief + Journal-Tabelle + Zusammenfassung, inhaltlich in allen Modi
      gleich vollständig, Teilen ueber System-Share-Sheet (kein Cloud-Upload)

## Phase 3 — Wissens-Bibliothek & Übungen
- [x] ContentBlock-Schema angelegt (additive Migration 3→4), Seed-Mechanismus (REPLACE
      bei jedem App-Start statt neuer Migration pro Textaenderung)
- [x] Warnzeichen-Block (Teil A) geseedet und in der Wissens-Bibliothek sichtbar —
      identischer Wortlaut in allen drei Modi, unabhängig von `variantenTiefe`/
      `sichtbarInModus`-Filterung (Sicherheitsluecke aus Blueprint-Abgleich geschlossen)
- [x] Restliche Buchteile B–H als ContentBlock geseedet (69 Zeilen, VOLL aus
      Manuskript, PEER/KURZ aus `content-varianten-texte.md`) — dabei einen
      adversarial gefundenen Bug behoben (VOLL/PEER teilten sich anfangs denselben
      Room-Primary-Key, REPLACE-Seed ueberschrieb die Hälfte des Contents)
- [x] Anzeige-Logik fuer variantenTiefe-gefilterte Bloecke: Modus filtert bereits
      seit der DAO-Query aus Phase 3 Commit 1 korrekt (KOMPASS→VOLL, PEER→PEER,
      QUICK→KURZ); zusätzlich Peer-Sortierung "Teil G prominent oben" ergaenzt
- [x] Glossar-Block (Teil H) in allen Modi sichtbar (VOLL=PEER geteilt, eigene
      KURZ-Zeile fuer Quick), live auf Emulator in allen drei Modi verifiziert
- [x] Übungs-Begleiter mit Timer/Wiederholzähler (HWS-Mobilisation, vestibuläres
      Training) — Inhalte aus Teil E, per-Übung eigenständiger Zustand (Timer setzt
      sich bei Übungswechsel zurück), Quick-Modus zeigt Hinweistext statt leerer
      Liste (Teil E dort laut Content-Mapping nicht sichtbar)
- [x] PEER-/KURZ-Textfassungen der ContentBlocks — fertig, siehe
      `content-varianten-texte.md`

## Phase 4 — Komfort & Erinnerungen
- [x] WorkManager-Erinnerung zum täglichen Nachtragen — zentraler Scheduler
      (Configuration.Provider + HiltWorkerFactory), zeigt nur, wenn heute noch kein
      Eintrag existiert, End-to-End auf Emulator verifiziert (Job geplant →
      force-getriggert → Benachrichtigung zugestellt → Selbst-Neuplanung +24h)
- [x] Einstellungen: Erinnerung an/aus + Uhrzeit (additive Migration 4→5,
      POST_NOTIFICATIONS-Laufzeitberechtigung nur bei Bedarf abgefragt)
- [ ] Ampel-Symbolik anpassen (Barrierefreiheit) — noch offen
- [ ] Reflexionsfragen-Pool für Peer-Modus hinterlegen (Inhalt noch zu definieren)

## Bewusst zurückgestellt (siehe PROJECT_LOG.md, Abschnitt 5)
- Cloud-Sync/Backup
- Wearable-Integration
- Mehrsprachigkeit
