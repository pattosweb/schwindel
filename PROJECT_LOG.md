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
11. `Blueprint-Abgleich: Peer-Reflexionstext-Vorschau im Journal-Verlauf ergaenzt`
12. `Phase 0.5b: Steckbrief-UI (Person, Medikamente, Vorerkrankungen, Ansprechpartner)`
13. `Phase 2: 60/30-Tage-Journalfenster (Room-Migration 2->3)`
14. `Phase 2: Zeitverlaufs-Diagramm + Muster-Erkennung`
15. `Phase 2: PDF-Export fuers Arztgespraech (Bordmittel)`
16. `Phase 2: Auswertung in den Journal-Verlauf integriert`
17. `Doku: Phase 2 (Auswertung) abgehakt, Luecken nachgezogen`
18. `Phase 3: ContentBlock-Schema (Room-Migration 3->4)`
19. `Phase 3: Warnzeichen-Inhalt geseedet (Sicherheitsluecke geschlossen)`
20. `Phase 3: Wissens-Bibliothek zeigt Warnzeichen-Block (Platzhalter abgeloest)`
21. `Doku: Warnzeichen-Sicherheitsluecke geschlossen, Prioritaet Phase 4 begruendet`
22. `Phase 4: WorkManager + Erinnerungs-Schema (Room-Migration 4->5)`
23. `Phase 4: ReminderWorker + zentraler Scheduler (Hilt-WorkManager)`
24. `Phase 4: Erinnerung in Einstellungen bedienbar`
25. `Doku: Phase 4 (WorkManager-Erinnerung) abgehakt`
26. `Doku: ganzheitlicher Rundgang (CLAUDE.md Regel 6) nach Phase 0-4`
27. `Phase 3: Buchteile B-H als ContentBlock geseedet (69 neue Zeilen)`
28. `Phase 3: Anzeige-Logik fuer variantenTiefe-gefilterte Bloecke ergaenzt`
29. `Übungs-Begleiter: Teil-E-Übungen mit Timer und Wiederholungszähler`
30. `Phase 4 Rest: Ampel-Farben mit hohem Kontrast (Barrierefreiheit)` (Room-Migration 5->6)
31. `Phase 4 Rest: Reflexionsfragen-Pool für Peer-Modus`
32. `Fix: Steckbrief-Geburtsjahr als scrollbare Jahr-Auswahl statt Zifferntastatur`

Details/Verifikation zu 1–4 und 7–10: Test-Gate (`ktlintCheck`, `detekt`,
`assembleDebug`, `testDebugUnitTest`, `connectedDebugAndroidTest`) grün vor jedem
Commit; manueller Durchlauf auf einem headless x86_64-Emulator (Android 15,
SwiftShader-Software-Rendering, da keine KVM-Gruppenmitgliedschaft in dieser
Dev-Umgebung) verifiziert. Phase 0/0.5: Onboarding → Modus wählen → Hauptshell →
Einstellungen → Moduswechsel → App-Kill → Neustart → Modus bleibt erhalten. Phase 1:
Onboarding → Kompass → Ampel wählen → Details aufklappen → Trigger-Tag setzt
Situation → Begleitsymptom wählen → Speichern → Eintrag erscheint im Journal-Verlauf;
Moduswechsel zu Quick zeigt korrekt das minimale 1-Freitextzeilen-Formular.
Phase 0.5b: Einstellungen → Steckbrief öffnen → Geburtsjahr/Beruf/Medikament/
Ansprechpartner ausfüllen → Medikament/Ansprechpartner sind sofort persistiert (App-Kill
überlebt) → Profil-Speichern-Button → App-Kill → Neustart → alle Felder korrekt erhalten.
Phase 2: 7 Testeinträge per SQL geseedet → Diagramm zeigt korrekte Tagesfarben,
Muster-Karte plausible Häufigkeits-Hinweise → PDF-Export erzeugt (per pdftoppm visuell
geprüftes) 3-seitiges PDF, Share-Sheet öffnet korrekt → Moduswechsel zu Quick zeigt
60-Tage-Fenster ohne Muster-Karte → Fenster-Erweitern-Toggle → App-Kill → Neustart →
60-Tage-Fenster bleibt aktiv.
Phase 3 (Warnzeichen-Block): Wissens-Bibliothek geoeffnet → Warnzeichen-Block korrekt
formatiert (Ueberschriften/Aufzaehlungen/fett) sichtbar, optisch als Sicherheitshinweis
abgesetzt → Modus zu Quick gewechselt → identischer Block weiterhin sichtbar (istWarn-
zeichenInhalt-Override bestaetigt, unabhaengig von variantenTiefe/sichtbarInModus).
Phase 4 (Erinnerung): Einstellungen → "Ja" → POST_NOTIFICATIONS-Dialog → erteilt →
Job im JobScheduler bestaetigt (dumpsys jobscheduler) → per "cmd jobscheduler run -f"
force-getriggert → Benachrichtigung tatsaechlich zugestellt (dumpsys notification:
korrekter Titel/Text/Channel/PendingIntent) → Worker hat sich selbst fuer +24h neu
eingeplant → "Nein" → Job storniert, reminderAktiviert korrekt auf 0 persistiert.
Phase 3 (Buchteile B-H): Seed-Count in der DB nachgezählt (70 ContentBlock-Zeilen,
nicht nur Build-Erfolg vertraut) → dabei den VOLL/PEER-Primary-Key-Bug gefunden und
behoben → nach Fix erneut nachgezählt (korrekt 70). Wissens-Bibliothek in allen drei
Modi geprüft: Kompass zeigt VOLL-Blöcke inkl. Überschriften/Aufzählungen korrekt
formatiert, Peer zeigt Teil G direkt nach dem Warnzeichen-Block (Prominenz bestaetigt),
Quick zeigt ausschließlich Warnzeichen + die 11 Kurzglossar-Einträge.
Übungs-Begleiter (Teil E, Timer/Wiederholzähler): Kompass zeigt alle 6 Übungen mit
VOLL-Text, Timer startet bei 1:00, +15s/-15s während Pause bedienbar, Start→Pause
nach 3s korrekt auf 0:56 heruntergezählt, +1-Wiederholung persistiert innerhalb der
Detailansicht → Zurück zur Liste → andere Übung geöffnet → eigener, frischer
Timer/Zähler-Zustand bestätigt (kein Bleed-over zwischen Übungen). Peer zeigt
dieselben 6 Übungen mit PEER-Ich-Erzählung-Text. Quick zeigt korrekt den
Hinweistext statt leerer Liste (Teil E dort nicht sichtbar). 14/14
connectedDebugAndroidTest weiterhin grün.
Ampel-Kontrast-Barrierefreiheit: Migration 5->6 adversarial getestet
(MigrationTestHelper: bestehendes Profil inkl. Erinnerungs-Einstellungen bleibt
beim Upgrade unangetastet, neue Spalte nutzbar). Live auf Emulator: Einstellungen
→ "Kontrastreiche Ampel-Farben" → Ja → Persistenz per SQL bestätigt → Schnell-
Erfassung zeigt Grün/Rot jetzt in Blau/Rotviolett statt Grün/Rot (Screenshot-
geprüft) → Journal-Verlauf-Diagramm, -Legende und Eintragskarten uebernehmen die
Palette konsistent → zurück auf "Nein" → Standardfarben wiederhergestellt.
Reflexionsfragen-Pool: Peer-Modus gewählt → Schnell-Erfassung zeigt eine der 14
Fragen statt des alten festen Prompts (tagesabhängig, deterministisch).
15/15 Tests weiterhin grün (neuer Migrationstest inklusive).

**Beim Live-Test gefunden und gefixt:** `LocalTime.toString()` zeigte in der UI
Nanosekunden ("13:19:10.668105") — zentrale `formatiereUhrzeit()`-Hilfsfunktion
(HH:mm) eingeführt und in Schnell-Erfassung + Journal-Verlauf verwendet.

## 4a. Ganzheitlicher Rundgang (CLAUDE.md Regel 6) — 08.09.2026
Erster kompletter Durchgang als echter Nutzer nach Abschluss der Phasen 0–4 (bisher
nur pro-Feature verifiziert, nie am Stueck): Frisch-Install → Onboarding (Kompass) →
Schnell-Erfassung (Ampel + Speichern) → Journal-Verlauf (Diagramm/Muster-Karte/PDF-
Button) → Wissens-Bibliothek (Warnzeichen-Block) → Übungs-Begleiter (Platzhalter) →
Einstellungen → Steckbrief. Bewusst mit Ein-Hand-Bedienung simuliert (nur Tap, keine
Mehrfinger-Gesten).

**Ergebnis:** Keine neuen Bugs, kein Absturz, keine Sackgasse. Alle bereits bekannten
Luecken (siehe Abschnitt 5) weiterhin akkurat, keine zusaetzlichen gefunden.

**Neue Beobachtung (Drei-Perspektiven-Prinzip, UX-/Betroffenen-Sicht):** Elemente am
oberen Bildschirmrand (z. B. die erste Ampel-Karte "Grün" in der Schnell-Erfassung,
der "Zurück"-Button oben im Steckbrief) sind bei Einhandhaltung mit dem Daumen auf
grossen Phones die strukturell am schwersten erreichbare Zone — ein allgemeines
Phaenomen grosser Touchscreens, kein Implementierungsfehler dieser App, aber relevant
fuer kuenftige Screens (Uebungs-Begleiter, weitere Content-Bloecke): primaere Aktionen
nach Moeglichkeit im unteren/mittleren Bildschirmbereich platzieren. Kein akuter
Handlungsbedarf jetzt (System-Zurueck-Geste bleibt die eigentliche Ein-Hand-Route),
aber bei der naechsten UI-Ueberarbeitung mitdenken.

## 4b. Zweiter ganzheitlicher Rundgang (CLAUDE.md Regel 6) — 08.09.2026, nach Phase 3 Rest + Phase 4 Rest
Kompletter Durchgang nach Fresh-Install (DB leer) mit allen seit dem ersten Rundgang
(Abschnitt 4a) neuen Features: Kompass → Onboarding → Schnell-Erfassung (Ampel Gelb,
Details aufklappen, Trigger-Tag, Dauer, Begleitsymptom) → Speichern → Journal-Verlauf
(Diagramm + plausibler Muster-Hinweis aus dem einen Eintrag) → PDF-Export (Share-Sheet
öffnet) → Wissens-Bibliothek (Warnzeichen oben, Buchteile B–H darunter) → Übungs-
Begleiter-Liste → Einstellungen → Steckbrief (Geburtsjahr + Medikament) → Speichern →
App-Kill → Neustart → Geburtsjahr/Medikament korrekt erhalten (SQL bestaetigt) →
Modus zu Peer → Reflexionsfrage aus dem neuen Pool sichtbar → Wissens-Bibliothek zeigt
Teil G direkt nach dem Warnzeichen-Block (Prominenz erneut bestaetigt) → Modus zu Quick
→ minimales Formular (Ampel + 1 Freitextzeile) → Speichern → Verlauf zeigt 30-Tage-
Fenster ohne Muster-Karte, beide bisherigen Eintraege weiterhin sichtbar (kein
Datenverlust ueber Moduswechsel) → Wissens-Bibliothek zeigt nur Warnzeichen + 11
Kurzglossar-Eintraege → Übungs-Begleiter zeigt korrekten Hinweistext → Einstellungen:
Erinnerung an → POST_NOTIFICATIONS erteilt → Job im JobScheduler bestaetigt (dumpsys)
→ wieder aus → Barrierefreiheit-Sektion koexistiert sauber mit Erinnerung-Sektion.

**Ergebnis:** Keine neuen Bugs, kein Absturz (logcat auf FATAL EXCEPTION geprueft),
keine Sackgasse, kein Datenverlust bei Moduswechsel. Alle Phase-0–4-Funktionen inkl.
der in dieser Sitzung neu ergaenzten (Übungs-Begleiter, Ampel-Kontrast,
Reflexionsfragen-Pool) end-to-end nutzbar bestaetigt. Bereits bekannte Luecken (siehe
Abschnitt 5) unveraendert, keine neuen gefunden. Volles Test-Gate vor dem Rundgang
erneut gruen (ktlintCheck, detekt, assembleDebug, testDebugUnitTest,
connectedDebugAndroidTest 15/15).

## 4c. Bugfix: Steckbrief-Geburtsjahr (Nutzer-Feedback 08.09.2026)
Patrick: "im steckbrief kann man das geburtsjahr nicht eintragen und ein
datenscroller ist auch nicht vorhanden". Ursache: freies Zifferntextfeld statt
eines antippbaren Auswahl-Elements wie bei den uebrigen Datumsfeldern - passt
nicht zur uebrigen App und ist bei eingeschraenkter Feinmotorik unzuverlaessiger
als ein Scroller. Ersetzt durch neue `JahrAuswahl`-Komponente (Button oeffnet
scrollbare Jahresliste, automatisch zum aktuellen Wert gescrollt). Live auf
Emulator verifiziert: Button oeffnet Liste, Drag-Scroll funktioniert ueber
mehrere Bildschirme (1979→1972→1965 getestet), Tippen auf ein Jahr uebernimmt es
und schliesst den Dialog, Speichern → App-Kill → Neustart → Wert korrekt via
SQL bestaetigt (1969 erhalten). 15/15 Tests weiterhin gruen.

## 4d. Phase 5: Mehrsprachigkeit (Nutzerwunsch 09.09.2026)
Patrick wollte Mehrsprachigkeit angehen: "deutsch, englisch, polnisch, russisch,
französisch, türkisch, arabisch wären glaube ich die richtigen sprachen". Sprach-
auswahl fachlich bestaetigt (deckt die groessten nicht-deutschsprachigen Patienten-
gruppen in D/A/CH ab). Wegen des Umfangs (kompletter Buchinhalt + ~150 UI-Strings +
RTL fuer Arabisch) mit Patrick auf zwei Entscheidungen geeinigt:
1. Uebersetzungs-QA: KI-Entwurf ueberall, aber Warnzeichen-Uebersetzungen in
   Nicht-DE/EN-Sprachen werden bei Einfuehrung explizit als "noch keine mutter-
   sprachliche/medizinische Pruefung" markiert (dieser Abschnitt).
2. Phasierung: erst Grundgeruest + Englisch komplett fertig, Rest (PL/RU/FR/TR/AR)
   folgt separat.

**Umgesetzt:** `Sprache`-Enum + additive Migration 6->7 (user_profile.sprache
nullable, content_block.sprache Default DE) + `LocalSprache`-CompositionLocal
(zentral in SchwindeljournalNavGraph, RTL-Layoutrichtung vorbereitet) + komplette
UI-Lokalisierung (alle Screens/Komponenten, Enum-Anzeigenamen mit sprache-Parameter,
Spracheingabe-Diktat folgt App-Sprache) + Einstellungen-Sprachschalter (Automatisch/
Deutsch/English) + vollstaendige englische Uebersetzung des Buchinhalts (Warnzeichen +
Teile A-H, alle VOLL/PEER/KURZ-Varianten, ~70 Zeilen).

**Sicherheitskritischer Warnzeichen-Text:** FAST-Test-Passage lehnt sich an
etablierte, oeffentlich verbreitete Schlaganfall-Aufklaerung an (Face-Arms-Speech-
Time) statt frei zu uebersetzen - reduziert das Risiko einer fachlich ungenauen
Übersetzung an der sicherheitskritischsten Stelle der App. Rest des englischen
Warnzeichen-Texts und aller anderen Buchteile ist ein KI-Entwurf.

**Adversariale QA (Nutzerfrage "wie loesen wir das mit dem Warnzeichenblocktest"):**
Genau die Bug-Klasse aus Abschnitt 4 (Primary-Key-Kollision beim REPLACE-Seed) kann
sich mit einer weiteren Dimension (Sprache) wiederholen - jede EN-Zeile hat daher
ein `-en`-id-Suffix, UND es gibt jetzt den ersten echten Unit-Test des Projekts
(`ContentSeedTest`, JVM, kein Android/Room noetig): keine doppelten ids ueber alle
Sprachen/Varianten, pro verfuegbarer Sprache genau ein nicht-leerer Warnzeichen-
Block, alle Buchteile A-H pro Sprache abgedeckt, DE/EN zeilengleich. Alle 4 Faelle
gruen.

**Live auf Emulator verifiziert (System-Locale Englisch):** Fresh Install -> Onboarding
komplett auf Englisch (automatische Systemsprachen-Erkennung) -> Kompass-Erfassung
(Ampel/Details/Trigger-Tags/Dauer/Symptome alle uebersetzt) -> Journal-Verlauf
(Timeline/Legende/Muster-Karte/PDF-Button) -> Wissens-Bibliothek (englischer
Warnzeichen-Block korrekt formatiert) -> Uebungs-Begleiter (alle 6 Uebungen +
Timer/Zaehler uebersetzt) -> Einstellungen (Sprachschalter Automatisch/Deutsch/
English, Live-Wechsel ohne Neustart bestaetigt, beide Richtungen) -> Steckbrief
(inkl. Jahr-Auswahl-Dialog "Birth year"/"Cancel") -> explizit Englisch gewaehlt ->
App-Kill -> Neustart -> Sprache korrekt erhalten (SQL bestaetigt). Kein Crash
(logcat FATAL EXCEPTION geprueft). Volles Test-Gate gruen: ktlintCheck, detekt,
assembleDebug, testDebugUnitTest (4/4 neue Tests), connectedDebugAndroidTest
(16/16 inkl. neuem 6->7-Migrationstest).

**PDF-Export bleibt bewusst Deutsch** (nicht mitlokalisiert in dieser Phase, siehe
Abschnitt 5) - `Ampel.anzeigename()`/`beschreibung()`/`SymptomTyp.anzeigename()`
haben einen `sprache`-Parameter mit Default `Sprache.DE`, PdfZeichner-Aufrufe bleiben
daher unveraendert.

## 5. Bekannte Lücken / bewusst außerhalb des Scopes
- RBAC: entfällt, Single-User-Offline-App (siehe CLAUDE.md)
- Rate Limiting: entfällt, keine öffentlichen Endpunkte (siehe CLAUDE.md)
- Cloud-Sync/Backup: bewusst nicht in Phase 1, da Datenschutz-Grundsatz "Daten bleiben
  auf dem Gerät" zunächst Priorität hat
- Wearable-Integration (Herzfrequenz/Schlafdaten): denkbare spätere Erweiterung, nicht
  Teil des aktuellen Scopes
- PEER- und KURZ-Textfassungen der ContentBlocks: **fertig**, siehe
  `content-varianten-texte.md` (steht bereit als Seed-Grundlage für Claude Code)
- Reflexionsfragen-Pool für den Peer-Modus: **fertig**, siehe Abschnitt 4 (Phase 4
  Rest) - inkl. englischer Fassung, siehe Abschnitt 4d
- Mehrsprachigkeit: Grundgerüst + Deutsch/Englisch **fertig** (Abschnitt 4d).
  Polnisch/Russisch/Französisch/Türkisch/Arabisch noch offen - Architektur ist
  bereit, reine Übersetzungsarbeit + RTL-Test bei Arabisch. Warnzeichen-Übersetzungen
  in diesen Sprachen brauchen vor Produktivnutzung eine muttersprachliche/fachliche
  Prüfung (bisher nur DE/EN, DE original, EN mit FAST-Test-Sonderbehandlung, siehe
  Abschnitt 4d)
- PDF-Export bleibt vorerst nur Deutsch, auch wenn die App-Sprache Englisch ist -
  bewusste Scope-Entscheidung für Phase 5, kein technisches Hindernis (Enum-
  Anzeigenamen haben bereits einen sprache-Parameter, PdfZeichner nutzt einfach
  weiterhin den Default DE) - bei Bedarf später nachziehen
- Bottom-Navigation: Label "Einstellungen" bricht bei 5 Items in zwei Zeilen um
  (kosmetisch, Touch-Ziel bleibt ≥48dp, Symbol+Text weiterhin erkennbar) — bei
  Gelegenheit der eigentlichen Screen-Umsetzung mit-lösen (z. B. kürzeres Label oder
  angepasstes Nav-Layout), kein eigener Task noetig
- Dev-Umgebung dieser Session hat keine KVM-Gruppenmitgliedschaft → Emulator lief nur
  mit SwiftShader-Software-Rendering (langsamer, aber funktional); für flüssige
  manuelle Tests auf Patricks Maschine ggf. `sudo usermod -aG kvm $USER` prüfen
- **Steckbrief-UI (Person/Beruf/Vorerkrankungen/Medikamente/Ansprechpartner):**
  **geschlossen 08.09.2026**, siehe Phase 0.5b in der Roadmap.
- Steckbrief "Zur Person"/Vorerkrankungen/Blutdruck-Felder gehen beim Verlassen des
  Screens ohne Tap auf "Speichern" kommentarlos verloren (Medikamente/Ansprechpartner
  sind davon nicht betroffen, die speichern sofort beim Hinzufügen). Kein Datenverlust
  bereits gespeicherter Werte, nur der ungespeicherte Draft — aber keine Warnung beim
  Verlassen. Kleiner UX-Polish, kein akutes Risiko, da nichts kommentarlos überschrieben
  wird; bei Gelegenheit mit "ungespeicherte Änderungen"-Hinweis versehen.
- **Standard-Journal-Dauer (60/30 Tage):** **geschlossen 08.09.2026**, siehe Phase 2
  in der Roadmap (Migration 2→3 + Fenster-Toggle im Quick-Modus).
- **Korrelationsansicht deckt Schlafqualität nicht ab:** `schlafqualitaetNachtDavor`
  ist Freitext (String?, keine Skala) modelliert — eine belastbare Häufigkeits-
  auszaehlung wie bei Begleitsymptomen/Trigger-Tags ist damit nicht sauber möglich,
  ohne Freitext zu interpretieren (Risiko falscher Muster). "Mögliche Muster" bleibt
  daher auf strukturierte Felder beschränkt. Wäre eine strukturierte
  Schlafqualitäts-Skala gewünscht, ist das eine Datenmodell-Entscheidung fuer
  Patrick, keine stillschweigende App-Erweiterung.
- **PDF-Journal-Tabelle:** lange Situationstexte werden pro Zelle abgeschnitten statt
  umgebrochen (siehe `lc-debt`-Kommentar in `PdfZeichner.kt`) — Inhalt bleibt lesbar,
  aber nicht immer vollständig auf einen Blick; Upgrade-Pfad dokumentiert im Code.

## 5a. Scope-Entscheidung vom 08.09.2026: Multi-Varianten-Architektur
Ursprünglich war die App als Begleiter zu einem einzelnen Buch geplant. Auf Basis der
Überlegung, drei Buchvarianten (Kompass/Peer/Quick) anzubieten, wurde entschieden, dass
die App **alle drei Varianten in einer Anwendung mit gemeinsamem Datenkern** abbildet,
statt drei separate Apps zu bauen. Details siehe CLAUDE.md, Abschnitt
"Multi-Varianten-Architektur", und `datenmodell-und-content-mapping.md`.

## 6. Nächste Schritte
Keine offenen Roadmap-Punkte mehr (Phase 0–4 vollständig abgehakt, siehe Roadmap-
Datei). Verbleibend nur kleinere, bewusst zurückgestellte Punkte unter Abschnitt 5
("Bekannte Lücken"), z. B.:
- Journal-Verlauf zeigt weiterhin nur eine einfache Liste statt "voller Tabelle"
  (Vorgriff aus Phase 1) — bei Bedarf später ausbauen
- Steckbrief-Draft-Verlustwarnung, PDF-Zellenumbruch (lc-debt), Schlafqualität nicht
  in der Muster-Analyse — alle unverändert seit Abschnitt 5, kein akuter Bedarf

## 7. Nutzer-Arbeitsweise
Siehe CLAUDE.md, Abschnitt "Nutzer-Arbeitsweise".
