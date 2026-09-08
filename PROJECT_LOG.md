# PROJECT_LOG — Schwindeljournal-App

Vollständige, unverkürzte Projekt-Dokumentation. Stand: 07.09.2026.

Verwandte Dateien:
- `roadmap-schwindeljournal-app.md` — laufend gepflegte Phasen-Checkliste
- `datenmodell-und-content-mapping.md` — technische Wahrheitsquelle für die
  Multi-Varianten-Architektur (Kompass/Peer/Quick), Room-Schema und Content-Mapping
- `content-varianten-texte.md` — fertige PEER- und KURZ-Textfassungen aller
  ContentBlocks, einsatzbereit als Seed-Grundlage
- `../schwindeljournal-buch/manuskript-entwurf.md` — Quellmaterial (VOLL-Fassung) für
  Journal-Datenmodell und Wissensinhalte der In-App-Bibliothek

## 1. Warum dieses Projekt existiert
Siehe CLAUDE.md, Abschnitt "Warum dieses Projekt existiert".

## 2. Tech-Stack & Umgebung
Siehe CLAUDE.md, Abschnitt "Tech-Stack & Umgebung". Git-Remote:
`git@github.com:pattosweb/schwindel.git`. Lokaler Projektpfad: `~/projects/schwindel`
(gleichzeitig Repo-Root, Standard-Gradle-Projektstruktur ab Phase 0). applicationId:
`app.schwindeljournal` (bewusst ohne Personen-/Firmenbezug). minSdk 26 / compileSdk und
targetSdk 35. DI: Hilt. Gradle 8.10.2 (Wrapper committed).

## 3. Arbeitsweise / Konventionen
Siehe CLAUDE.md — vollständig übernommen, keine Abweichung.

## 4. Vollständige Commit-Historie
Ab 08.09.2026: erste Commits (Phase 0 + Phase 0.5 + Phase 1, siehe Roadmap).
1. `Phase 0: Android-Projekt-Grundgeruest (Compose, Navigation, ktlint/detekt)`
2. `Phase 0.5: Room-Datenschicht fuer Steckbrief/Modus (Version 1)`
3. `Phase 0.5: Hilt-DI + Onboarding-Flow`
4. `Phase 0.5: zentraler Modus-State + Einstellungen-Screen`
5. `Konzept-Dokumentation: CLAUDE.md, Roadmap, Datenmodell, Skills, Manuskript`
6. `Doku: Roadmap Phase 0/0.5 abgehakt, PROJECT_LOG nachgezogen`
7. `Phase 1: JournalEntry/Symptom-Schema (Room-Migration 1->2)`
8. `Phase 1: wiederverwendbare Erfassungs-Bausteine (Ampel, Trigger-Tags, Sprache)`
9. `Phase 1: Schnell-Erfassung modus-abhaengig (Kompass/Peer/Quick)`
10. `Phase 1 (Vorgriff): einfache Journal-Verlauf-Liste`

Details/Verifikation zu 1–4 und 7–10: Test-Gate (`ktlintCheck`, `detekt`,
`assembleDebug`, `testDebugUnitTest`, `connectedDebugAndroidTest`) grün vor jedem
Commit; manueller Durchlauf auf einem headless x86_64-Emulator (Android 15,
SwiftShader-Software-Rendering, da keine KVM-Gruppenmitgliedschaft in dieser
Dev-Umgebung) verifiziert. Phase 0/0.5: Onboarding → Modus wählen → Hauptshell →
Einstellungen → Moduswechsel → App-Kill → Neustart → Modus bleibt erhalten. Phase 1:
Onboarding → Kompass → Ampel wählen → Details aufklappen → Trigger-Tag setzt
Situation → Begleitsymptom wählen → Speichern → Eintrag erscheint im Journal-Verlauf;
Moduswechsel zu Quick zeigt korrekt das minimale 1-Freitextzeilen-Formular.

**Beim Live-Test gefunden und gefixt:** `LocalTime.toString()` zeigte in der UI
Nanosekunden ("13:19:10.668105") — zentrale `formatiereUhrzeit()`-Hilfsfunktion
(HH:mm) eingeführt und in Schnell-Erfassung + Journal-Verlauf verwendet.

## 5. Bekannte Lücken / bewusst außerhalb des Scopes
- RBAC: entfällt, Single-User-Offline-App (siehe CLAUDE.md)
- Rate Limiting: entfällt, keine öffentlichen Endpunkte (siehe CLAUDE.md)
- Cloud-Sync/Backup: bewusst nicht in Phase 1, da Datenschutz-Grundsatz "Daten bleiben
  auf dem Gerät" zunächst Priorität hat
- Wearable-Integration (Herzfrequenz/Schlafdaten): denkbare spätere Erweiterung, nicht
  Teil des aktuellen Scopes
- PEER- und KURZ-Textfassungen der ContentBlocks: **fertig**, siehe
  `content-varianten-texte.md` (steht bereit als Seed-Grundlage für Claude Code)
- Reflexionsfragen-Pool für den Peer-Modus inhaltlich noch nicht definiert
- Bottom-Navigation: Label "Einstellungen" bricht bei 5 Items in zwei Zeilen um
  (kosmetisch, Touch-Ziel bleibt ≥48dp, Symbol+Text weiterhin erkennbar) — bei
  Gelegenheit der eigentlichen Screen-Umsetzung mit-lösen (z. B. kürzeres Label oder
  angepasstes Nav-Layout), kein eigener Task noetig
- Dev-Umgebung dieser Session hat keine KVM-Gruppenmitgliedschaft → Emulator lief nur
  mit SwiftShader-Software-Rendering (langsamer, aber funktional); für flüssige
  manuelle Tests auf Patricks Maschine ggf. `sudo usermod -aG kvm $USER` prüfen
- **Blueprint-Abgleich 08.09.2026 (`datenmodell-und-content-mapping.md` Abschnitt 2):**
  Steckbrief-UI (Medikamente/Vorerkrankungen/Ansprechpartner bearbeiten) fehlt komplett
  — Room-Entities existieren seit Phase 0.5, aber kein Screen dazu, und keine
  Roadmap-Phase weist das aktuell explizit zu. Laut Blueprint sicherheitsrelevant
  ("Steckbrief-Pflichtfelder Medikamente/Vorerkrankungen ... in keinem Modus gekürzt
  oder versteckt") — sollte vor Phase 2/3 nachgezogen werden, Patrick entscheidet
  Zeitpunkt/Priorität
- **Blueprint-Abgleich 08.09.2026:** "Standard-Journal-Dauer" (60 Tage Kompass/Peer,
  30 Tage Quick mit Erweiterungs-Option) ist in der aktuellen Journal-Verlauf-Liste
  noch nicht umgesetzt — zeigt aktuell alle Einträge ohne Zeitfenster-Begrenzung;
  sinnvoll im Rahmen des Journal-Verlauf-Ausbaus (siehe Abschnitt 6) mitzulösen

## 5a. Scope-Entscheidung vom 08.09.2026: Multi-Varianten-Architektur
Ursprünglich war die App als Begleiter zu einem einzelnen Buch geplant. Auf Basis der
Überlegung, drei Buchvarianten (Kompass/Peer/Quick) anzubieten, wurde entschieden, dass
die App **alle drei Varianten in einer Anwendung mit gemeinsamem Datenkern** abbildet,
statt drei separate Apps zu bauen. Details siehe CLAUDE.md, Abschnitt
"Multi-Varianten-Architektur", und `datenmodell-und-content-mapping.md`.

## 6. Nächste Schritte
- Phase 2: Zeitverlaufs-Diagramm (Ampelfarbe über Zeit), Korrelationsansicht
  (Kompass/Peer), PDF-Export fürs Arztgespräch
- Journal-Verlauf-Screen ausbauen: aktuell nur einfache chronologische Liste
  (Vorgriff aus Phase 1), volle Tabelle/Detailansicht pro Eintrag fehlt noch
- Parallel: PEER-/KURZ-Textfassungen der ContentBlocks stehen bereits fertig in
  `content-varianten-texte.md` — Seed für Phase 3 (`ContentBlock`-Tabelle)

## 7. Nutzer-Arbeitsweise
Siehe CLAUDE.md, Abschnitt "Nutzer-Arbeitsweise".
