package info.imdang.imdang.common.util

import java.time.Duration
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

fun Long.toLocalDateTime(): LocalDateTime = LocalDateTime
    .ofInstant(Instant.ofEpochMilli(this), ZoneId.systemDefault())

fun Long.toLocalDate(): LocalDate = toLocalDateTime().toLocalDate()

fun Long.diffDays(): Int {
    val now = LocalDateTime.now()
    return Duration.between(toLocalDateTime(), now).toDays().toInt()
}

fun LocalDate.isToday() = isEqual(LocalDate.now())

fun nowDateTimeToString(format: String): String {
    return LocalDateTime.now().format(DateTimeFormatter.ofPattern(format))
}

fun String.formatDate(fromFormat: String, toFormat: String): String {
    val dateTime = LocalDate.parse(this, DateTimeFormatter.ofPattern(fromFormat))
    return dateTime.format(DateTimeFormatter.ofPattern(toFormat))
}
