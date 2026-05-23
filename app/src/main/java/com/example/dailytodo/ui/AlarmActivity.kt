package com.example.dailytodo.ui

import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.dailytodo.core.Constants
import com.example.dailytodo.ui.alarmscreen.AlarmScreen
import com.example.dailytodo.ui.theme.DailyTodoTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AlarmActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
            setShowWhenLocked(true)
            setTurnScreenOn(true)
        } else {
            @Suppress("DEPRECATION")
            window.addFlags(
                WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED or
                        WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON or
                        WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
            )
        }

        val dayOfWeek = intent.getIntExtra(Constants.EXTRA_DAY_OF_WEEK, -1)

        setContent {
            DailyTodoTheme {
                AlarmScreen(
                    dayOfWeekValue = dayOfWeek,
                    onDismiss = { finish() }
                )
            }
        }
    }
}
