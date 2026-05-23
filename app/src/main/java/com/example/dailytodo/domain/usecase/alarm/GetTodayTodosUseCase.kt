package com.example.dailytodo.domain.usecase.alarm

import com.example.dailytodo.domain.model.Todo
import com.example.dailytodo.domain.repository.TodoRepository
import java.time.LocalDate
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTodayTodosUseCase @Inject constructor(
    private val repository: TodoRepository
) {
    operator fun invoke(): Flow<List<Todo>> {
        val today = LocalDate.now().dayOfWeek
        return repository.getTodosByDay(today)
    }
}
