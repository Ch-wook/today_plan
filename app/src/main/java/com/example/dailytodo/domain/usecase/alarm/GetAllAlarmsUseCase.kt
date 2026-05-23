package com.example.dailytodo.domain.usecase.alarm

import com.example.dailytodo.domain.model.DayAlarm
import com.example.dailytodo.domain.repository.AlarmRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllAlarmsUseCase @Inject constructor(
    private val repository: AlarmRepository
) {
    operator fun invoke(): Flow<List<DayAlarm>> = repository.getAllAlarms()
}
