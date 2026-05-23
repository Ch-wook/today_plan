package com.example.dailytodo.domain.repository

import com.example.dailytodo.domain.model.DayAlarm
import java.time.DayOfWeek
import kotlinx.coroutines.flow.Flow

interface AlarmRepository {
    fun getAllAlarms(): Flow<List<DayAlarm>>
    suspend fun getAlarmByDay(dayOfWeek: DayOfWeek): DayAlarm?
    suspend fun setAlarm(alarm: DayAlarm)
}
