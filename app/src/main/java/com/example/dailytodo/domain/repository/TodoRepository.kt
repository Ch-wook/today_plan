package com.example.dailytodo.domain.repository

import com.example.dailytodo.domain.model.Supply
import com.example.dailytodo.domain.model.Todo
import java.time.DayOfWeek
import kotlinx.coroutines.flow.Flow

interface TodoRepository {
    fun getTodosByDay(dayOfWeek: DayOfWeek): Flow<List<Todo>>
    suspend fun addTodo(todo: Todo): Long
    suspend fun updateTodo(todo: Todo)
    suspend fun deleteTodo(todo: Todo)
    suspend fun addSupply(supply: Supply): Long
    suspend fun updateSupply(supply: Supply)
    suspend fun deleteSupply(supply: Supply)
    suspend fun resetCheckedForDay(dayOfWeek: DayOfWeek)
}
