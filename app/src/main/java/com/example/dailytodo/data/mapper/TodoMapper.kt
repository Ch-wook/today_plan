package com.example.dailytodo.data.mapper

import com.example.dailytodo.data.local.entity.AlarmEntity
import com.example.dailytodo.data.local.entity.SupplyEntity
import com.example.dailytodo.data.local.entity.TodoEntity
import com.example.dailytodo.data.local.relation.TodoWithSupplies
import com.example.dailytodo.domain.model.DayAlarm
import com.example.dailytodo.domain.model.Supply
import com.example.dailytodo.domain.model.Todo
import java.time.DayOfWeek

fun TodoWithSupplies.toDomain() = Todo(
    id = todo.id,
    title = todo.title,
    dayOfWeek = DayOfWeek.of(todo.dayOfWeek),
    isChecked = todo.isChecked,
    orderIndex = todo.orderIndex,
    supplies = supplies.map { it.toDomain() }
)

fun Todo.toEntity() = TodoEntity(
    id = id,
    title = title,
    dayOfWeek = dayOfWeek.value,
    isChecked = isChecked,
    orderIndex = orderIndex
)

fun SupplyEntity.toDomain() = Supply(id = id, todoId = todoId, name = name, isChecked = isChecked)

fun Supply.toEntity() = SupplyEntity(id = id, todoId = todoId, name = name, isChecked = isChecked)

fun AlarmEntity.toDomain() = DayAlarm(
    dayOfWeek = DayOfWeek.of(dayOfWeek),
    hour = hour,
    minute = minute,
    isEnabled = isEnabled
)

fun DayAlarm.toEntity() = AlarmEntity(
    dayOfWeek = dayOfWeek.value,
    hour = hour,
    minute = minute,
    isEnabled = isEnabled
)
