package com.example.dailytodo.di

import android.content.Context
import androidx.room.Room
import com.example.dailytodo.core.Constants
import com.example.dailytodo.data.local.AppDatabase
import com.example.dailytodo.data.local.dao.AlarmDao
import com.example.dailytodo.data.local.dao.TodoDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, Constants.DATABASE_NAME).build()

    @Provides
    fun provideTodoDao(db: AppDatabase): TodoDao = db.todoDao()

    @Provides
    fun provideAlarmDao(db: AppDatabase): AlarmDao = db.alarmDao()
}
