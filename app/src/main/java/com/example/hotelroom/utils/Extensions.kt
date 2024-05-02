package com.example.hotelroom.utils

import java.text.NumberFormat
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

fun Double.toVietnameseCurrency(): String {
    val format: NumberFormat = NumberFormat.getCurrencyInstance(Locale("vi", "VN"))
    return format.format(this)
}

fun Long.convertTimestampToDateTime(): String {
    val instant = Instant.ofEpochMilli(this)
    val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy").withZone(ZoneId.systemDefault())
    return formatter.format(instant)
}

fun Long.convertTimestampToMonth(): String {
    val instant = Instant.ofEpochMilli(this)
    val formatter = DateTimeFormatter.ofPattern("MM").withZone(ZoneId.systemDefault())
    return formatter.format(instant)
}

fun Long.convertTimestampToYear(): String {
    val instant = Instant.ofEpochMilli(this)
    val formatter = DateTimeFormatter.ofPattern("yyyy").withZone(ZoneId.systemDefault())
    return formatter.format(instant)
}

fun Long.convertTimestampToDay(): String {
    val instant = Instant.ofEpochMilli(this)
    val formatter = DateTimeFormatter.ofPattern("dd").withZone(ZoneId.systemDefault())
    return formatter.format(instant)
}