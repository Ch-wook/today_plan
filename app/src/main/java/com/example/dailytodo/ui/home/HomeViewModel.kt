package com.example.dailytodo.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dailytodo.domain.model.Supply
import com.example.dailytodo.domain.model.Todo
import com.example.dailytodo.domain.usecase.todo.DeleteTodoUseCase
import com.example.dailytodo.domain.usecase.todo.GetTodosByDayUseCase
import com.example.dailytodo.domain.usecase.todo.ToggleSupplyCheckUseCase
import com.example.dailytodo.domain.usecase.todo.ToggleTodoCheckUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.DayOfWeek
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getTodosByDay: GetTodosByDayUseCase,
    private val toggleTodoCheck: ToggleTodoCheckUseCase,
    private val toggleSupplyCheck: ToggleSupplyCheckUseCase,
    private val deleteTodo: DeleteTodoUseCase
) : ViewModel() {

    private val _selectedDay = MutableStateFlow(LocalDate.now().dayOfWeek)
    val selectedDay: StateFlow<DayOfWeek> = _selectedDay

    val todos: StateFlow<List<Todo>> = _selectedDay
        .flatMapLatest { day -> getTodosByDay(day) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun selectDay(day: DayOfWeek) {
        _selectedDay.value = day
    }

    fun toggleTodo(todo: Todo) {
        viewModelScope.launch { toggleTodoCheck(todo) }
    }

    fun toggleSupply(supply: Supply) {
        viewModelScope.launch { toggleSupplyCheck(supply) }
    }

    fun deleteTodo(todo: Todo) {
        viewModelScope.launch { deleteTodo.invoke(todo) }
    }
}
