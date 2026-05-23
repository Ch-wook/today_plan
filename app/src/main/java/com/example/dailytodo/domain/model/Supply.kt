package com.example.dailytodo.domain.model

data class Supply(
    val id: Long = 0,
    val todoId: Long = 0,
    val name: String,
    val isChecked: Boolean = false
)
