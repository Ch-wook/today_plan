package com.example.dailytodo.data.repository

import com.example.dailytodo.data.local.dao.AlarmDao
import com.example.dailytodo.data.mapper.toDomain
import com.example.dailytodo.data.mapper.toEntity
import com.example.dailytodo.domain.model.DayAlarm
import com.example.dailytodo.domain.repository.AlarmRepository
import java.time.DayOfWeek
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AlarmRepositoryImpl @Inject constructor(
    private val alarmDao: AlarmDao
) : AlarmRepository {

    override fun getAllAlarms(): Flow<List<DayAlarm>> =
        alarmDao.getAllAlarms().map { list -> list.map { it.toDomain() } }

    override suspend fun getAlarmByDay(dayOfWeek: DayOfWeek): DayAlarm? =
        alarmDao.getAlarmByDay(dayOfWeek.value)?.toDomain()

    override suspend fun setAlarm(alarm: DayAlarm) = alarmDao.upsertAlarm(alarm.toEntity())
}
