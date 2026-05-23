package com.example.dailytodo.domain.usecase.todo

import com.example.dailytodo.domain.model.Supply
import com.example.dailytodo.domain.model.Todo
import com.example.dailytodo.domain.repository.TodoRepository
import javax.inject.Inject

class AddTodoUseCase @Inject constructor(
    private val repository: TodoRepository
) {
    suspend operator fun invoke(todo: Todo, supplies: List<Supply>) {
        val todoId = repository.addTodo(todo)
        supplies.forEach { supply ->
            repository.addSupply(supply.copy(todoId = todoId))
        }
    }
}
