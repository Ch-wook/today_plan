package com.example.dailytodo.domain.usecase.alarm

import com.example.dailytodo.alarm.AlarmScheduler
import com.example.dailytodo.domain.model.DayAlarm
import com.example.dailytodo.domain.repository.AlarmRepository
import javax.inject.Inject

class SetDayAlarmUseCase @Inject constructor(
    private val alarmRepository: AlarmRepository,
    private val alarmScheduler: AlarmScheduler
) {
    suspend operator fun invoke(alarm: DayAlarm) {
        alarmRepository.setAlarm(alarm)
        if (alarm.isEnabled) {
            alarmScheduler.schedule(alarm)
        } else {
            alarmScheduler.cancel(alarm.dayOfWeek)
        }
    }
}
