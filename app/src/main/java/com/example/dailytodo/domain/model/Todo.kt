package com.example.dailytodo.domain.model

import java.time.DayOfWeek

data class Todo(
    val id: Long = 0,
    val title: String,
    val dayOfWeek: DayOfWeek,
    val isChecked: Boolean = false,
    val orderIndex: Int = 0,
    val supplies: List<Supply> = emptyList()
)
