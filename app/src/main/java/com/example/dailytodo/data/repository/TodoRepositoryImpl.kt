package com.example.dailytodo.data.repository

import com.example.dailytodo.data.local.dao.TodoDao
import com.example.dailytodo.data.mapper.toDomain
import com.example.dailytodo.data.mapper.toEntity
import com.example.dailytodo.domain.model.Supply
import com.example.dailytodo.domain.model.Todo
import com.example.dailytodo.domain.repository.TodoRepository
import java.time.DayOfWeek
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TodoRepositoryImpl @Inject constructor(
    private val todoDao: TodoDao
) : TodoRepository {

    override fun getTodosByDay(dayOfWeek: DayOfWeek): Flow<List<Todo>> =
        todoDao.getTodosWithSuppliesByDay(dayOfWeek.value).map { list ->
            list.map { it.toDomain() }
        }

    override suspend fun addTodo(todo: Todo): Long = todoDao.insertTodo(todo.toEntity())

    override suspend fun updateTodo(todo: Todo) = todoDao.updateTodo(todo.toEntity())

    override suspend fun deleteTodo(todo: Todo) = todoDao.deleteTodo(todo.toEntity())

    override suspend fun addSupply(supply: Supply): Long = todoDao.insertSupply(supply.toEntity())

    override suspend fun updateSupply(supply: Supply) = todoDao.updateSupply(supply.toEntity())

    override suspend fun deleteSupply(supply: Supply) = todoDao.deleteSupply(supply.toEntity())

    override suspend fun resetCheckedForDay(dayOfWeek: DayOfWeek) =
        todoDao.resetCheckedForDay(dayOfWeek.value)
}
