package br.com.joaovq.lunarappcompose.core.utils.ext

import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

fun String.toLocalDateTimeFormatted(): String? {
    try {
        val publishedAt = OffsetDateTime.parse(this)
        val publishedAtFormatted = DateTimeFormatter.ofPattern("dd MMMM yyyy").format(publishedAt)
        return publishedAtFormatted
    } catch (e: Exception) {
        e.printStackTrace()
        return null
    }
}