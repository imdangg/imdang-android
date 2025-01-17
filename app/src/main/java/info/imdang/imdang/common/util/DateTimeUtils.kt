package info.imdang.imdang.common.util

import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

fun Long.toLocalDate(): LocalDate = LocalDateTime
    .ofInstant(Instant.ofEpochMilli(this), ZoneId.systemDefault())
    .toLocalDate()

fun LocalDate.isToday() = isEqual(LocalDate.now())

fun nowDateTimeToString(format: String): String {
    return LocalDateTime.now().format(DateTimeFormatter.ofPattern(format))
}

fun String.formatDate(fromFormat: String, toFormat: String): String {
    val dateTime = LocalDate.parse(this, DateTimeFormatter.ofPattern(fromFormat))
    return dateTime.format(DateTimeFormatter.ofPattern(toFormat))
}
