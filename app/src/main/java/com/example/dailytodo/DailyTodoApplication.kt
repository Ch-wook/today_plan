package com.example.dailytodo

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import com.example.dailytodo.core.Constants
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class DailyTodoApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        val channel = NotificationChannel(
            Constants.CHANNEL_ID,
            "일일 할 일 알림",
            NotificationManager.IMPORTANCE_HIGH
        ).apply {
            description = "매일 할 일 알림 채널"
            setShowBadge(true)
        }
        getSystemService(NotificationManager::class.java).createNotificationChannel(channel)
    }
}
