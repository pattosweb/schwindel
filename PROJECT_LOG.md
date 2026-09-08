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
Siehe CLAUDE.md, Abschnitt "Tech-Stack & Umgebung". Noch offen: konkreter Git-Remote und
lokaler Projektpfad, werden bei Repo-Anlage nachgetragen.

## 3. Arbeitsweise / Konventionen
Siehe CLAUDE.md — vollständig übernommen, keine Abweichung.

## 4. Vollständige Commit-Historie
Noch keine Commits — Projekt befindet sich in der Konzeptionsphase (Stand 07.09.2026).

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

## 5a. Scope-Entscheidung vom 08.09.2026: Multi-Varianten-Architektur
Ursprünglich war die App als Begleiter zu einem einzelnen Buch geplant. Auf Basis der
Überlegung, drei Buchvarianten (Kompass/Peer/Quick) anzubieten, wurde entschieden, dass
die App **alle drei Varianten in einer Anwendung mit gemeinsamem Datenkern** abbildet,
statt drei separate Apps zu bauen. Details siehe CLAUDE.md, Abschnitt
"Multi-Varianten-Architektur", und `datenmodell-und-content-mapping.md`.

## 6. Nächste Schritte
- Repo anlegen, Grundgerüst (Gradle-Projekt, Compose-Setup) erstellen
- Room-Datenmodell exakt nach `datenmodell-und-content-mapping.md` Abschnitt 1 anlegen
- Modus-Auswahl (Onboarding + Einstellungen) als früher vertikaler Durchstich, da sie
  alle weiteren UI-Entscheidungen beeinflusst
- Schnell-Erfassungs-Screen (3-Tap-Eintrag) modus-abhängig nach Abschnitt 2 der
  Datenmodell-Datei bauen
- Parallel dazu: PEER-/KURZ-Textfassungen der ContentBlocks redaktionell erstellen
  (Blueprint-Skill `skill-app-content-varianten`, siehe skills/-Ordner)

## 7. Nutzer-Arbeitsweise
Siehe CLAUDE.md, Abschnitt "Nutzer-Arbeitsweise".
