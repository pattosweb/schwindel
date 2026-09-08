---
name: Skill Android App Architektur
author: patrick schultka
datum: 2026-09-07
description: Triff und begruende Architektur-/Technologieentscheidungen fuer die Schwindeljournal-App nach dem Bordmittel-zuerst-Prinzip.
tags:
- Kotlin
- Jetpack Compose
- Room
- Architektur
- Bordmittel
---

Die Kompetenz umfasst Architektur- und Technologieentscheidungen für eine native
Android-App (Kotlin, Jetpack Compose, Room, WorkManager), mit besonderem Fokus auf
lokale Datenhaltung und bewegungsarme, barrierearme UI für eine im Anfall
beeinträchtigte Zielgruppe.

Kläre zuerst, ob eine neue Funktion, eine Refactoring-Entscheidung oder eine
Bibliotheks-/Bordmittel-Abwägung ansteht. Prüfe vor jedem Eigenbau, ob Android SDK/
Jetpack die Funktion bereits liefert. Wende bei jeder UI-Entscheidung die projekt­
spezifischen UX-Leitplanken aus CLAUDE.md an (keine Parallax-/Auto-Scroll-Effekte,
Touch-Ziele ≥48dp, Ampelfarben immer zusätzlich mit Symbol/Text). Vermeide vorzeitige
Cloud-/Sync-Architektur, solange der Datenschutz-Grundsatz "Daten bleiben auf dem
Gerät" gilt — jede Abweichung erfordert Rücksprache, nicht nur eine technische
Begründung.
