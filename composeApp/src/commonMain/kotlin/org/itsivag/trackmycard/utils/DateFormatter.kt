package org.itsivag.trackmycard.utils

import io.github.aakira.napier.Napier
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format.DateTimeFormat
import kotlinx.datetime.format.FormatStringsInDatetimeFormats
import kotlinx.datetime.format.byUnicodePattern
import kotlinx.datetime.toLocalDateTime

fun formatDateTime(timestamp: Long, format: String): String? {
    try {
        val instant = Instant.fromEpochMilliseconds(timestamp)
        val localDateTime = instant.toLocalDateTime(TimeZone.currentSystemDefault())
        val formatPattern = format

        @OptIn(FormatStringsInDatetimeFormats::class) val dateTimeFormat = LocalDateTime.Format {
            byUnicodePattern(formatPattern)
        }
        return dateTimeFormat.format(localDateTime)
    } catch (e: Exception) {
        Napier.e { e.toString() }
        return null
    }
}