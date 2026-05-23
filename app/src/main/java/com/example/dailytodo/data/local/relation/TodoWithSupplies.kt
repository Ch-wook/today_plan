package com.example.dailytodo.data.local.relation

import androidx.room.Embedded
import androidx.room.Relation
import com.example.dailytodo.data.local.entity.SupplyEntity
import com.example.dailytodo.data.local.entity.TodoEntity

data class TodoWithSupplies(
    @Embedded val todo: TodoEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "todoId"
    )
    val supplies: List<SupplyEntity>
)
