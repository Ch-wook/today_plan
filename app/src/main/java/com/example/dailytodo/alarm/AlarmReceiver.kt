package com.example.dailytodo.alarm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.dailytodo.core.Constants
import com.example.dailytodo.domain.model.DayAlarm
import com.example.dailytodo.domain.repository.AlarmRepository
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.DayOfWeek
import javax.inject.Inject

@AndroidEntryPoint
class AlarmReceiver : BroadcastReceiver() {

    @Inject lateinit var alarmNotificationHelper: AlarmNotificationHelper
    @Inject lateinit var alarmScheduler: AlarmScheduler
    @Inject lateinit var alarmRepository: AlarmRepository

    override fun onReceive(context: Context, intent: Intent) {
        val dayOfWeekValue = intent.getIntExtra(Constants.EXTRA_DAY_OF_WEEK, -1)
        if (dayOfWeekValue == -1) return

        alarmNotificationHelper.showFullScreenAlarm(dayOfWeekValue)

        // 다음 주 같은 요일로 재스케줄
        CoroutineScope(Dispatchers.IO).launch {
            val dayOfWeek = DayOfWeek.of(dayOfWeekValue)
            val alarm = alarmRepository.getAlarmByDay(dayOfWeek) ?: return@launch
            if (alarm.isEnabled) {
                alarmScheduler.schedule(
                    DayAlarm(dayOfWeek = dayOfWeek, hour = alarm.hour, minute = alarm.minute, isEnabled = true)
                )
            }
        }
    }
}
