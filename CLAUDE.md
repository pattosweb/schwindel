# CLAUDE.md — Schwindeljournal-App

## Warum dieses Projekt existiert
Digitale Begleitung zu den drei geplanten Schwindeljournal-Buchvarianten ("Mein
Schwindel-Kompass" — systematisch/vollständig, "Wenn sich alles dreht" — emotional/
peer-nah, "Schwindel im Blick" — minimalistisch/schnell). Die App bildet **alle drei
Varianten in einer einzigen Anwendung** ab: ein gemeinsamer Datenkern (Journal-Einträge,
Steckbrief, Wissens-Inhalte), aber ein wählbarer "Modus", der Tiefe, Tonalität und
Journal-Formular an die jeweilige Buchvariante anpasst. Kein Nutzer verliert beim
Moduswechsel Daten, weil das Datenmodell ein Superset aller drei Varianten ist (siehe
`datenmodell-und-content-mapping.md`). Zielgruppe ist während der Nutzung teilweise
motorisch/visuell beeinträchtigt (Schwindel-Anfall) — das bleibt die zentrale
Design-Restriktion, unabhängig vom gewählten Modus.

## Tech-Stack & Umgebung
- Repo: {{GIT_REMOTE — noch anzulegen}}
- Umgebung: Android-Projekt, lokale Entwicklung unter {{PROJEKTORDNER, z. B.
  ~/schwindeljournal-app}}
- Kernkomponenten: Kotlin, Jetpack Compose, Room (lokale SQLite-Persistenz), WorkManager
  (Erinnerungen/Scheduler), Android SpeechRecognizer (Diktat-Notizen, Bordmittel statt
  Cloud-STT), Vico oder Compose-native Charts (Musteranalyse)
- Dev-Server-/Pfad-Konvention: Standard Android-Studio-Gradle-Projektstruktur —
  Begründung: keine projektspezifische Abweichung nötig, Bordmittel-Konvention vermeidet
  spätere Reibung bei Tooling/CI

## Multi-Varianten-Architektur (zentral, nicht verhandelbar)
Ein Kern, drei Linsen — keine drei Codebasen, kein drei Datenbanken:

| Modus | Entspricht Buch | Journal-Umfang | Content-Tiefe | Tonalität |
|---|---|---|---|---|
| Kompass | "Mein Schwindel-Kompass" | Voller 8-Spalten-Eintrag + Steckbrief, 60 Tage | Vollständige Kapiteltexte (Teile A–H) | sachlich-systematisch |
| Peer | "Wenn sich alles dreht" | Wie Kompass, zusätzlich Reflexionsfrage/Freitext pro Tag | Narrative Kurzfassung + ausgebautes Teil G (Geschichten) | emotional, peer-nah, Ich-Erzählung |
| Quick | "Schwindel im Blick" | Reduziert auf Kernfelder (Uhrzeit, Ampel, Dauer, eine Freitextzeile), 30 Tage als Standard, 60 Tage optional | Nur Warnzeichen (Teil A-Auszug) + Kurzglossar | knapp, checklistenartig |

Details zu Datenmodell, Content-Tagging und der vollständigen Content-Mapping-Tabelle:
siehe `datenmodell-und-content-mapping.md` — diese Datei ist die technische
Wahrheitsquelle für die Varianten-Umsetzung und muss vor jeder Datenmodell-Änderung
konsultiert werden.

Der Moduswechsel in den Einstellungen ändert nur Anzeige/Formular-Umfang, niemals das
zugrunde liegende Schema — ein Nutzer, der von Quick zu Kompass wechselt, sieht rückwirkend
leere zusätzliche Felder bei seinen bisherigen Einträgen, verliert aber nichts.

## Arbeitsweise / Konventionen (verbindlich)
1. **Test-Gate:** Nach jeder Änderung `git add` + `commit` + `push`, aber nur wenn die
   Unit-/Instrumentation-Tests (`./gradlew test` / `./gradlew connectedAndroidTest`)
   vollständig grün sind und `ktlint`/`detekt` sauber durchlaufen. Roter Zustand wird nie
   gepusht — erst fixen, dann erneut testen.
2. **Risikobasiertes QA:** Bei Datenpersistenz (Room-Migrationen, Verschlüsselung/Backup
   sobald vorhanden) einen unabhängigen, adversarialen Prüf-Pass fahren, der aktiv
   versucht, Datenverlust oder Dateninkonsistenz zu erzeugen. Bei risikoarmer, rein
   darstellender UI-Arbeit reicht sorgfältiger Selbst-Review + manuelle Live-Verifikation
   auf einem echten/emulierten Gerät.
3. **Kleine Commits:** Bewusst in kleinen, abgeschlossenen Schritten committen.
4. **Wahrheitsquelle:** Bei Widerspruch zwischen `roadmap-schwindeljournal-app.md` und
   der Git-/Code-Historie gilt die Historie. Roadmap regelmäßig nachziehen.
5. **Vor "erledigt"-Markierung:** Verifizieren, dass ein Mensch den Flow tatsächlich in
   der laufenden App (Emulator oder Gerät) erreichen und abschließen kann — nicht nur,
   dass Tests grün sind.
6. **Ganzheitlicher Rundgang:** In regelmäßigen Abständen einen kompletten Durchgang als
   echter Nutzer machen — inklusive Test mit reduzierter Motorik/Ein-Hand-Bedienung, da
   das die reale Nutzungssituation ist.
7. **Drei-Perspektiven-Prinzip:** Jede Struktur-/Architekturentscheidung wird geprüft aus
   - Senior-Android-Entwickler-Sicht (technisch solide, wartbar)
   - UX-Sicht mit expliziter Prüfung auf Bewegungsarmut in der UI, große Touch-Ziele,
     Kontrast, Ein-Hand-Bedienbarkeit
   - Betroffenen-Sicht (deckt es echten Alltag im/nach dem Anfall real ab, ohne zu
     überfordern?)
   Fällt dabei eine fachliche Lücke auf, wird sie unter "Bekannte Lücken" in
   PROJECT_LOG.md vermerkt statt sofort umgesetzt — Patrick entscheidet Zeitpunkt/
   Reihenfolge; Umsetzung muss im bestehenden Stil erfolgen, nicht generisch.

## Technische Engineering-Standards
1. **Wiederverwendbarkeit:** Gemeinsame Logik (z. B. Trigger-Tag-Auswahl, Ampel-Widget)
   in eigene Composables/Use-Cases extrahieren statt zu duplizieren.
2. **Erst Bordmittel, dann Eigenbau:** Vor jeder Eigenentwicklung prüfen, ob
   Jetpack/Android SDK die Funktion bereits liefert (z. B. SpeechRecognizer statt
   Cloud-STT, WorkManager statt Eigenbau-Scheduler, Room statt Eigenbau-Persistenz).
3. **Erweiterbarkeit:** Datenmodell so anlegen, dass neue Journal-Felder (z. B. weitere
   Trigger-Kategorien aus späteren Buchauflagen) ohne Breaking Change ergänzt werden
   können (Room-Migrationen von Anfang an sauber versionieren).
4. **RBAC:** Entfällt — Single-User-Offline-App ohne Mehrnutzer-Zugriff. Explizit als
   "bewusst außerhalb des Scopes" in PROJECT_LOG.md vermerkt, nicht stillschweigend
   übergangen.
5. **Rate Limiting:** Entfällt zunächst — keine öffentlich erreichbaren Endpunkte. Sobald
   optionaler Cloud-Sync/Backup kommt, an dieser Stelle erneut prüfen.
6. **Production-Readiness:** Von der ersten Zeile an: Fehlerbehandlung statt Silent
   Failures (v. a. bei Room-Operationen), Logging über Timber, keine Secrets im Code
   (auch nicht für spätere Cloud-Anbindung).
7. **Skalierbarkeit:** Datenmodell muss mehrere Jahre täglicher Einträge performant
   halten (Indizes auf Datum/Trigger-Tags einplanen) — ohne auf Vorrat zu
   überkonstruieren.
8. **Queues statt Gleichzeitigkeit:** PDF-Export und ggf. spätere Bildverarbeitung über
   Kotlin Coroutines/WorkManager-Jobs abarbeiten, nicht UI-blockierend.
9. **Zentraler Scheduler:** Erinnerungen ausschließlich über WorkManager als zentralen
   Einstiegspunkt, keine verstreuten AlarmManager-Einzelaufrufe.

## Besondere UX-Leitplanken (projektspezifisch, nicht verhandelbar)
- Keine Parallax-Effekte, kein Auto-Scroll, keine unnötigen Animationen — Zielgruppe kann
  während/nach einem Schwindelanfall bewegungsempfindlich sein
- Touch-Ziele mindestens 48dp, keine filigranen Mehrfach-Tap-Interaktionen für die
  Schnell-Erfassung
- Jede Ampelfarbe zusätzlich mit Symbol/Text kennzeichnen (nicht nur Farbcodierung)
- Spracheingabe als gleichwertige Alternative zu jedem Freitextfeld

## Datenschutz-Grundsatz (projektspezifisch, nicht verhandelbar)
Gesundheitsdaten verlassen standardmäßig nicht das Gerät. Jede Funktion, die das ändern
würde (Cloud-Sync, Analytics, Crash-Reporting mit Nutzdaten), erfordert eine explizite,
separate Freigabe durch Patrick und wird nicht "nebenbei" mit einer anderen Funktion
eingeführt.

## Nutzer-Arbeitsweise
Patrick delegiert Architektur-/Implementierungsentscheidungen ("du bist der Profi und
entscheidest"), priorisiert so, dass sich Probleme nicht für spätere Erweiterungen
aufstauen. Das Datenmodell der App orientiert sich am Journal-Spaltenkatalog aus dem
Buchmanuskript (`schwindeljournal-buch/manuskript-entwurf.md`, Abschnitt 3) — bei
Änderungen am Buch-Journalformat ist dieses Datenmodell entsprechend nachzuziehen.
