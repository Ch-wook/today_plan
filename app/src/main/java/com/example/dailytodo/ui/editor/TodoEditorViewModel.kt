package com.example.dailytodo.ui.editor

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dailytodo.domain.model.Supply
import com.example.dailytodo.domain.model.Todo
import com.example.dailytodo.domain.usecase.todo.AddTodoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.DayOfWeek
import java.time.LocalDate
import javax.inject.Inject

data class EditorUiState(
    val title: String = "",
    val selectedDay: DayOfWeek = LocalDate.now().dayOfWeek,
    val supplies: List<String> = emptyList(),
    val isSaving: Boolean = false
)

@HiltViewModel
class TodoEditorViewModel @Inject constructor(
    private val addTodoUseCase: AddTodoUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(EditorUiState())
    val uiState: StateFlow<EditorUiState> = _uiState.asStateFlow()

    fun updateTitle(title: String) {
        _uiState.value = _uiState.value.copy(title = title)
    }

    fun selectDay(day: DayOfWeek) {
        _uiState.value = _uiState.value.copy(selectedDay = day)
    }

    fun addSupply(name: String) {
        if (name.isBlank()) return
        _uiState.value = _uiState.value.copy(
            supplies = _uiState.value.supplies + name.trim()
        )
    }

    fun removeSupply(index: Int) {
        _uiState.value = _uiState.value.copy(
            supplies = _uiState.value.supplies.toMutableList().also { it.removeAt(index) }
        )
    }

    fun save(onComplete: () -> Unit) {
        val state = _uiState.value
        if (state.title.isBlank()) return
        viewModelScope.launch {
            _uiState.value = state.copy(isSaving = true)
            val todo = Todo(title = state.title, dayOfWeek = state.selectedDay)
            val supplies = state.supplies.map { Supply(name = it) }
            addTodoUseCase(todo, supplies)
            onComplete()
        }
    }
}
