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

fun SymptomTyp.anzeigename(sprache: Sprache = Sprache.DE): String =
    if (sprache == Sprache.EN) anzeigenameEn() else anzeigenameDe()

private fun SymptomTyp.anzeigenameDe(): String =
    when (this) {
        SymptomTyp.OHRENSAUSEN -> "Ohrensausen"
        SymptomTyp.NACKENSCHMERZ -> "Nackenschmerz"
        SymptomTyp.KOPFSCHMERZ -> "Kopfschmerz"
        SymptomTyp.SEHSTOERUNG -> "Sehstörung"
        SymptomTyp.HERZKLOPFEN -> "Herzklopfen"
        SymptomTyp.SONSTIGES -> "Sonstiges"
    }

private fun SymptomTyp.anzeigenameEn(): String =
    when (this) {
        SymptomTyp.OHRENSAUSEN -> "Tinnitus"
        SymptomTyp.NACKENSCHMERZ -> "Neck pain"
        SymptomTyp.KOPFSCHMERZ -> "Headache"
        SymptomTyp.SEHSTOERUNG -> "Vision problems"
        SymptomTyp.HERZKLOPFEN -> "Heart palpitations"
        SymptomTyp.SONSTIGES -> "Other"
    }
