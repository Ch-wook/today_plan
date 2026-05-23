package com.example.dailytodo.ui.alarmscreen

import android.media.RingtoneManager
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@Composable
fun AlarmScreen(
    dayOfWeekValue: Int,
    onDismiss: () -> Unit,
    viewModel: AlarmViewModel = hiltViewModel()
) {
    val todos by viewModel.todos.collectAsStateWithLifecycle()
    var currentTime by remember { mutableStateOf(LocalTime.now()) }

    val context = LocalContext.current
    val ringtone = remember {
        val uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
        RingtoneManager.getRingtone(context, uri)
    }

    DisposableEffect(Unit) {
        ringtone?.play()
        onDispose { ringtone?.stop() }
    }

    LaunchedEffect(Unit) {
        while (true) {
            kotlinx.coroutines.delay(1000)
            currentTime = LocalTime.now()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF1A1A2E))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(48.dp))

            Text(
                text = currentTime.format(DateTimeFormatter.ofPattern("HH:mm")),
                fontSize = 72.sp,
                fontWeight = FontWeight.Light,
                color = Color.White
            )
            Text(
                text = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 (E)")),
                fontSize = 16.sp,
                color = Color(0xFFB0B0C0)
            )

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "오늘의 할 일",
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(16.dp))

            if (todos.isEmpty()) {
                Text(text = "오늘 할 일이 없습니다", color = Color(0xFFB0B0C0), fontSize = 16.sp)
            } else {
                LazyColumn(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(todos) { todo ->
                        Surface(
                            color = Color(0xFF16213E),
                            shape = MaterialTheme.shapes.medium,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Checkbox(
                                        checked = todo.isChecked,
                                        onCheckedChange = { viewModel.toggleTodo(todo) },
                                        colors = CheckboxDefaults.colors(
                                            checkmarkColor = Color(0xFF1A1A2E),
                                            checkedColor = Color(0xFF7C83FD)
                                        )
                                    )
                                    Text(text = todo.title, color = Color.White, fontSize = 16.sp)
                                }
                                todo.supplies.forEach { supply ->
                                    Row(
                                        modifier = Modifier.padding(start = 16.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Checkbox(
                                            checked = supply.isChecked,
                                            onCheckedChange = { viewModel.toggleSupply(supply) },
                                            colors = CheckboxDefaults.colors(
                                                checkmarkColor = Color(0xFF1A1A2E),
                                                checkedColor = Color(0xFF7C83FD)
                                            )
                                        )
                                        Text(text = supply.name, color = Color(0xFFB0B0C0), fontSize = 14.sp)
                                    }
                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = { ringtone?.stop(); onDismiss() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF7C83FD))
            ) {
                Text("알람 끄기", fontSize = 18.sp, color = Color.White)
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}
