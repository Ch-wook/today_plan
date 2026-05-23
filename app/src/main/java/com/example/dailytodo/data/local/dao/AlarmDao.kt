package com.example.dailytodo.data.local.dao

import androidx.room.*
import com.example.dailytodo.data.local.entity.AlarmEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AlarmDao {

    @Query("SELECT * FROM alarms ORDER BY dayOfWeek ASC")
    fun getAllAlarms(): Flow<List<AlarmEntity>>

    @Query("SELECT * FROM alarms WHERE dayOfWeek = :dayOfWeek")
    suspend fun getAlarmByDay(dayOfWeek: Int): AlarmEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertAlarm(alarm: AlarmEntity)
}
