package app.schwindeljournal.ui.components

import java.time.LocalTime
import java.time.format.DateTimeFormatter

private val uhrzeitFormat: DateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm")

/** Zeigt LocalTime ohne Sekunden-/Nanosekunden-Rauschen (Standard-toString() ist zu genau fuer die UI). */
fun formatiereUhrzeit(uhrzeit: LocalTime): String = uhrzeit.format(uhrzeitFormat)
