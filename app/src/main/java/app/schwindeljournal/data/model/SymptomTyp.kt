package app.schwindeljournal.data.model

/** Begleitsymptome, Buch Abschnitt 4.2 Spalte 6 (Mehrfachauswahl). */
enum class SymptomTyp {
    OHRENSAUSEN,
    NACKENSCHMERZ,
    KOPFSCHMERZ,
    SEHSTOERUNG,
    HERZKLOPFEN,
    SONSTIGES,
}

fun SymptomTyp.anzeigename(): String =
    when (this) {
        SymptomTyp.OHRENSAUSEN -> "Ohrensausen"
        SymptomTyp.NACKENSCHMERZ -> "Nackenschmerz"
        SymptomTyp.KOPFSCHMERZ -> "Kopfschmerz"
        SymptomTyp.SEHSTOERUNG -> "Sehstörung"
        SymptomTyp.HERZKLOPFEN -> "Herzklopfen"
        SymptomTyp.SONSTIGES -> "Sonstiges"
    }
