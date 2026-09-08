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
Ab 08.09.2026: erste Commits (Phase 0 + Phase 0.5, siehe Roadmap).
1. `Phase 0: Android-Projekt-Grundgeruest (Compose, Navigation, ktlint/detekt)`
2. `Phase 0.5: Room-Datenschicht fuer Steckbrief/Modus (Version 1)`
3. `Phase 0.5: Hilt-DI + Onboarding-Flow`
4. `Phase 0.5: zentraler Modus-State + Einstellungen-Screen`
5. `Konzept-Dokumentation: CLAUDE.md, Roadmap, Datenmodell, Skills, Manuskript`

Details/Verifikation zu 1–4: Test-Gate (`ktlintCheck`, `detekt`, `assembleDebug`,
`connectedDebugAndroidTest`) grün vor jedem Commit; manueller Durchlauf auf einem
headless x86_64-Emulator (Android 15, SwiftShader-Software-Rendering, da keine KVM-
Gruppenmitgliedschaft in dieser Dev-Umgebung) verifiziert: Onboarding → Modus wählen →
Hauptshell → Einstellungen → Moduswechsel → App-Kill → Neustart → Modus bleibt erhalten.

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

## 5a. Scope-Entscheidung vom 08.09.2026: Multi-Varianten-Architektur
Ursprünglich war die App als Begleiter zu einem einzelnen Buch geplant. Auf Basis der
Überlegung, drei Buchvarianten (Kompass/Peer/Quick) anzubieten, wurde entschieden, dass
die App **alle drei Varianten in einer Anwendung mit gemeinsamem Datenkern** abbildet,
statt drei separate Apps zu bauen. Details siehe CLAUDE.md, Abschnitt
"Multi-Varianten-Architektur", und `datenmodell-und-content-mapping.md`.

## 6. Nächste Schritte
- Phase 1: `JournalEntry`/`Symptom`-Schema (additive Room-Migration v1→v2) nach
  `datenmodell-und-content-mapping.md` Abschnitt 1
- Schnell-Erfassungs-Screen (3-Tap-Eintrag) modus-abhängig nach Abschnitt 2 der
  Datenmodell-Datei bauen (löst den aktuellen Platzhalter ab)
- Trigger-Tag-Bibliothek, Warnzeichen-Checkbox, Reflexionsfrage-Feld (nur Peer)
- Parallel dazu: PEER-/KURZ-Textfassungen der ContentBlocks redaktionell erstellen
  (Blueprint-Skill `skill-app-content-varianten`, siehe skills/-Ordner) — laut Roadmap
  bereits fertig, siehe `content-varianten-texte.md`

## 7. Nutzer-Arbeitsweise
Siehe CLAUDE.md, Abschnitt "Nutzer-Arbeitsweise".
