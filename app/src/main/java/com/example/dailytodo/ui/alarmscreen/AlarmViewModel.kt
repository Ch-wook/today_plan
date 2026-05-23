package com.example.dailytodo.ui.alarmscreen

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dailytodo.domain.model.Supply
import com.example.dailytodo.domain.model.Todo
import com.example.dailytodo.domain.repository.TodoRepository
import com.example.dailytodo.domain.usecase.alarm.GetTodayTodosUseCase
import com.example.dailytodo.domain.usecase.todo.ToggleSupplyCheckUseCase
import com.example.dailytodo.domain.usecase.todo.ToggleTodoCheckUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class AlarmViewModel @Inject constructor(
    private val getTodayTodos: GetTodayTodosUseCase,
    private val toggleTodoCheck: ToggleTodoCheckUseCase,
    private val toggleSupplyCheck: ToggleSupplyCheckUseCase,
    private val todoRepository: TodoRepository,
    @ApplicationContext private val context: Context
) : ViewModel() {

    private val prefs: SharedPreferences =
        context.getSharedPreferences("alarm_prefs", Context.MODE_PRIVATE)

    val todos: StateFlow<List<Todo>> = getTodayTodos()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    init {
        resetIfNeeded()
    }

    private fun resetIfNeeded() {
        val today = LocalDate.now().toString()
        val lastReset = prefs.getString("last_reset_date", null)
        if (lastReset != today) {
            viewModelScope.launch {
                todoRepository.resetCheckedForDay(LocalDate.now().dayOfWeek)
                prefs.edit().putString("last_reset_date", today).apply()
            }
        }
    }

    fun toggleTodo(todo: Todo) {
        viewModelScope.launch { toggleTodoCheck(todo) }
    }

    fun toggleSupply(supply: Supply) {
        viewModelScope.launch { toggleSupplyCheck(supply) }
    }
}
