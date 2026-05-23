package com.example.dailytodo.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.dailytodo.ui.home.components.DayTabRow
import com.example.dailytodo.ui.home.components.TodoItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onAddTodo: () -> Unit,
    onAlarmSettingClick: () -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val selectedDay by viewModel.selectedDay.collectAsStateWithLifecycle()
    val todos by viewModel.todos.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("오늘의 할 일") },
                actions = {
                    IconButton(onClick = onAlarmSettingClick) {
                        Icon(Icons.Default.Notifications, contentDescription = "알림 설정")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onAddTodo) {
                Icon(Icons.Default.Add, contentDescription = "할 일 추가")
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            DayTabRow(
                selectedDay = selectedDay,
                onDaySelected = viewModel::selectDay
            )
            if (todos.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "할 일이 없습니다.\n+ 버튼으로 추가해보세요!",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            } else {
                LazyColumn(
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(todos, key = { it.id }) { todo ->
                        TodoItem(
                            todo = todo,
                            onToggle = { viewModel.toggleTodo(todo) },
                            onSupplyToggle = viewModel::toggleSupply,
                            onDelete = { viewModel.deleteTodo(todo) }
                        )
                    }
                }
            }
        }
    }
}
