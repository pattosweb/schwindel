package app.schwindeljournal.ui.components

import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

private val uhrzeitFormat: DateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm")
private val datumFormat: DateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")

/** Zeigt LocalTime ohne Sekunden-/Nanosekunden-Rauschen (Standard-toString() ist zu genau fuer die UI). */
fun formatiereUhrzeit(uhrzeit: LocalTime): String = uhrzeit.format(uhrzeitFormat)

fun formatiereDatum(datum: LocalDate): String = datum.format(datumFormat)
