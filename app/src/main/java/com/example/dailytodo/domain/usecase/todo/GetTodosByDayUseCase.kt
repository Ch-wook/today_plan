package com.example.dailytodo.domain.usecase.todo

import com.example.dailytodo.domain.model.Todo
import com.example.dailytodo.domain.repository.TodoRepository
import java.time.DayOfWeek
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTodosByDayUseCase @Inject constructor(
    private val repository: TodoRepository
) {
    operator fun invoke(dayOfWeek: DayOfWeek): Flow<List<Todo>> =
        repository.getTodosByDay(dayOfWeek)
}
