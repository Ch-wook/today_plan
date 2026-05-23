package com.example.dailytodo.core.ext

import java.time.DayOfWeek
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.temporal.TemporalAdjusters

fun nextAlarmMillis(dayOfWeek: DayOfWeek, hour: Int, minute: Int): Long {
    val now = LocalDateTime.now()
    val target = now
        .with(TemporalAdjusters.nextOrSame(dayOfWeek))
        .withHour(hour)
        .withMinute(minute)
        .withSecond(0)
        .withNano(0)
    val adjusted = if (!target.isAfter(now)) target.plusWeeks(1) else target
    return adjusted.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
}

fun formatTime(hour: Int, minute: Int): String {
    val amPm = if (hour < 12) "오전" else "오후"
    val h = if (hour % 12 == 0) 12 else hour % 12
    return "$amPm ${h}:${minute.toString().padStart(2, '0')}"
}
