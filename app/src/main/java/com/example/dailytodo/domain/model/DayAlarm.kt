package com.example.dailytodo.domain.model

import java.time.DayOfWeek

data class DayAlarm(
    val dayOfWeek: DayOfWeek,
    val hour: Int,
    val minute: Int,
    val isEnabled: Boolean = false
)
