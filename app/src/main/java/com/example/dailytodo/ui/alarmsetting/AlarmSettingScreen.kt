package com.example.dailytodo.ui.alarmsetting

import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.dailytodo.core.ext.formatTime
import com.example.dailytodo.domain.model.DayAlarm
import java.time.DayOfWeek

private val dayNames = mapOf(
    DayOfWeek.MONDAY to "월요일",
    DayOfWeek.TUESDAY to "화요일",
    DayOfWeek.WEDNESDAY to "수요일",
    DayOfWeek.THURSDAY to "목요일",
    DayOfWeek.FRIDAY to "금요일",
    DayOfWeek.SATURDAY to "토요일",
    DayOfWeek.SUNDAY to "일요일"
)

private val orderedDays = listOf(
    DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY,
    DayOfWeek.THURSDAY, DayOfWeek.FRIDAY, DayOfWeek.SATURDAY, DayOfWeek.SUNDAY
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlarmSettingScreen(
    onBack: () -> Unit,
    viewModel: AlarmSettingViewModel = hiltViewModel()
) {
    val alarms by viewModel.alarms.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val canScheduleExact = viewModel.canScheduleExactAlarms()
    val canUseFullScreen = viewModel.canUseFullScreenIntent()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("알림 설정") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "뒤로")
                    }
                }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            if (!canScheduleExact) {
                item {
                    PermissionWarningCard(
                        message = "정확한 알람을 위해 권한이 필요합니다.",
                        actionLabel = "권한 설정"
                    ) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                            context.startActivity(Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM))
                        }
                    }
                }
            }
            if (!canUseFullScreen) {
                item {
                    PermissionWarningCard(
                        message = "전체화면 알림을 위해 권한이 필요합니다.",
                        actionLabel = "권한 설정"
                    ) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
                            val intent = Intent(Settings.ACTION_MANAGE_APP_USE_FULL_SCREEN_INTENT).apply {
                                data = Uri.parse("package:${context.packageName}")
                            }
                            context.startActivity(intent)
                        }
                    }
                }
            }
            items(orderedDays) { day ->
                val alarm = alarms.find { it.dayOfWeek == day }
                AlarmDayItem(
                    day = day,
                    alarm = alarm,
                    onToggle = {
                        alarm?.let { viewModel.toggleAlarm(it) }
                            ?: viewModel.setAlarm(day, 8, 0, true)
                    },
                    onTimeChange = { hour, minute ->
                        viewModel.setAlarm(day, hour, minute, alarm?.isEnabled ?: false)
                    },
                    context = context
                )
            }
        }
    }
}

@Composable
private fun AlarmDayItem(
    day: DayOfWeek,
    alarm: DayAlarm?,
    onToggle: () -> Unit,
    onTimeChange: (Int, Int) -> Unit,
    context: Context
) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = dayNames[day] ?: "",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.weight(1f)
            )
            TextButton(onClick = {
                val h = alarm?.hour ?: 8
                val m = alarm?.minute ?: 0
                TimePickerDialog(context, { _, hour, minute ->
                    onTimeChange(hour, minute)
                }, h, m, false).show()
            }) {
                Text(
                    text = if (alarm != null) formatTime(alarm.hour, alarm.minute) else "시간 설정",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            Switch(
                checked = alarm?.isEnabled ?: false,
                onCheckedChange = { onToggle() }
            )
        }
    }
}

@Composable
private fun PermissionWarningCard(
    message: String,
    actionLabel: String,
    onAction: () -> Unit
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.errorContainer)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = message,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.weight(1f),
                color = MaterialTheme.colorScheme.onErrorContainer
            )
            TextButton(onClick = onAction) { Text(actionLabel) }
        }
    }
}
