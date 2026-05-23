package com.example.dailytodo.data.local.dao

import androidx.room.*
import com.example.dailytodo.data.local.entity.SupplyEntity
import com.example.dailytodo.data.local.entity.TodoEntity
import com.example.dailytodo.data.local.relation.TodoWithSupplies
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoDao {

    @Transaction
    @Query("SELECT * FROM todos WHERE dayOfWeek = :dayOfWeek ORDER BY orderIndex ASC")
    fun getTodosWithSuppliesByDay(dayOfWeek: Int): Flow<List<TodoWithSupplies>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTodo(todo: TodoEntity): Long

    @Update
    suspend fun updateTodo(todo: TodoEntity)

    @Delete
    suspend fun deleteTodo(todo: TodoEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSupply(supply: SupplyEntity): Long

    @Update
    suspend fun updateSupply(supply: SupplyEntity)

    @Delete
    suspend fun deleteSupply(supply: SupplyEntity)

    @Query("UPDATE todos SET isChecked = 0 WHERE dayOfWeek = :dayOfWeek")
    suspend fun resetCheckedForDay(dayOfWeek: Int)
}
