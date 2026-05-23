package com.example.dailytodo.alarm

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.example.dailytodo.core.Constants
import com.example.dailytodo.ui.AlarmActivity
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AlarmNotificationHelper @Inject constructor(
    @ApplicationContext private val context: Context
) {
    fun showFullScreenAlarm(dayOfWeekValue: Int) {
        val fullScreenIntent = Intent(context, AlarmActivity::class.java).apply {
            putExtra(Constants.EXTRA_DAY_OF_WEEK, dayOfWeekValue)
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
        }
        val fullScreenPendingIntent = PendingIntent.getActivity(
            context,
            dayOfWeekValue + Constants.REQUEST_CODE_BASE,
            fullScreenIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(context, Constants.CHANNEL_ID)
            .setSmallIcon(android.R.drawable.ic_lock_idle_alarm)
            .setContentTitle("오늘의 할 일")
            .setContentText("할 일을 확인하세요")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setCategory(NotificationCompat.CATEGORY_ALARM)
            .setFullScreenIntent(fullScreenPendingIntent, true)
            .setContentIntent(fullScreenPendingIntent)
            .setAutoCancel(true)
            .build()

        context.getSystemService(NotificationManager::class.java)
            .notify(dayOfWeekValue, notification)
    }
}
