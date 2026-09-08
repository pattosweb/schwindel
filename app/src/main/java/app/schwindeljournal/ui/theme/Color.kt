package app.schwindeljournal.ui.theme

import androidx.compose.ui.graphics.Color

val SchwindelBlauLight = Color(0xFF3D5A80)
val SchwindelBlauDark = Color(0xFFA9C9E8)
val AmpelGruen = Color(0xFF2E7D32)
val AmpelGelb = Color(0xFFF9A825)
val AmpelRot = Color(0xFFC62828)

// Barrierefreiheit (Phase 4): alternative Ampel-Palette nach Okabe-Ito, wählbar in
// den Einstellungen. Rot/Grün ist die haeufigste Farbfehlsichtigkeit (Deuteranopie/
// Protanopie) - diese Palette ersetzt Gruen/Rot durch Blau/Rotviolett, die sich auch
// bei Rot-Gruen-Schwaeche klar voneinander unterscheiden lassen (Symbol+Text bleiben
// ohnehin in jedem Fall zusaetzlich vorhanden, siehe CLAUDE.md UX-Leitplanken).
val AmpelGruenKontrast = Color(0xFF0072B2)
val AmpelGelbKontrast = Color(0xFFE69F00)
val AmpelRotKontrast = Color(0xFFCC79A7)
