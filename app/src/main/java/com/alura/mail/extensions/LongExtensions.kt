package com.alura.mail.extensions

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


fun Long.toFormattedDate(): String {
    val currentDate = System.currentTimeMillis()
    val diffMillis = currentDate - this
    val passedDays = diffMillis / (1000 * 60 * 60 * 24)

    val last24Hours = 24 * 60 * 60 * 1000
    val lastWeek = 7

    val formatString = when {
        diffMillis <= last24Hours -> "HH:mm"
        passedDays <= lastWeek -> "d 'de' MMM"
        else -> "dd/MM/yyyy"
    }
    val formatter = SimpleDateFormat(formatString, Locale.getDefault())
    return formatter.format(Date(this))
}