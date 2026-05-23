package com.example.dailytodo.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "supplies",
    foreignKeys = [ForeignKey(
        entity = TodoEntity::class,
        parentColumns = ["id"],
        childColumns = ["todoId"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index("todoId")]
)
data class SupplyEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val todoId: Long,
    val name: String,
    val isChecked: Boolean = false
)
