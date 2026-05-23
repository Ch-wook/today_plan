package com.example.dailytodo.domain.usecase.todo

import com.example.dailytodo.domain.model.Todo
import com.example.dailytodo.domain.repository.TodoRepository
import javax.inject.Inject

class DeleteTodoUseCase @Inject constructor(
    private val repository: TodoRepository
) {
    suspend operator fun invoke(todo: Todo) {
        repository.deleteTodo(todo)
    }
}
