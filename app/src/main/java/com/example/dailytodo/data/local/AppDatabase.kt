package com.example.dailytodo.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.dailytodo.data.local.dao.AlarmDao
import com.example.dailytodo.data.local.dao.TodoDao
import com.example.dailytodo.data.local.entity.AlarmEntity
import com.example.dailytodo.data.local.entity.SupplyEntity
import com.example.dailytodo.data.local.entity.TodoEntity

@Database(
    entities = [TodoEntity::class, SupplyEntity::class, AlarmEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun todoDao(): TodoDao
    abstract fun alarmDao(): AlarmDao
}
