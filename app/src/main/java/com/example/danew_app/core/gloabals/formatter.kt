package com.example.danew_app.core.gloabals

import java.time.LocalDate
import java.time.format.DateTimeFormatter

fun formatDateToString(date: LocalDate): String {
    val formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd")
    return date.format(formatter)
}
