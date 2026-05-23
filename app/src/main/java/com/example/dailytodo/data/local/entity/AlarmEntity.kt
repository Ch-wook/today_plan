package com.example.dailytodo.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "alarms")
data class AlarmEntity(
    @PrimaryKey val dayOfWeek: Int,
    val hour: Int,
    val minute: Int,
    val isEnabled: Boolean = false
)
