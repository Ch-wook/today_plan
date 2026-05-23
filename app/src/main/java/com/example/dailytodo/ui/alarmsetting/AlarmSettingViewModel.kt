package com.example.dailytodo.ui.alarmsetting

import android.app.AlarmManager
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dailytodo.domain.model.DayAlarm
import com.example.dailytodo.domain.usecase.alarm.GetAllAlarmsUseCase
import com.example.dailytodo.domain.usecase.alarm.SetDayAlarmUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.DayOfWeek
import javax.inject.Inject

@HiltViewModel
class AlarmSettingViewModel @Inject constructor(
    private val getAllAlarms: GetAllAlarmsUseCase,
    private val setDayAlarm: SetDayAlarmUseCase,
    @ApplicationContext private val context: Context
) : ViewModel() {

    val alarms: StateFlow<List<DayAlarm>> = getAllAlarms()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun canScheduleExactAlarms(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            context.getSystemService(AlarmManager::class.java).canScheduleExactAlarms()
        } else true
    }

    fun canUseFullScreenIntent(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
            context.getSystemService(NotificationManager::class.java).canUseFullScreenIntent()
        } else true
    }

    fun setAlarm(dayOfWeek: DayOfWeek, hour: Int, minute: Int, enabled: Boolean) {
        viewModelScope.launch {
            setDayAlarm(DayAlarm(dayOfWeek = dayOfWeek, hour = hour, minute = minute, isEnabled = enabled))
        }
    }

    fun toggleAlarm(alarm: DayAlarm) {
        viewModelScope.launch {
            setDayAlarm(alarm.copy(isEnabled = !alarm.isEnabled))
        }
    }
}
