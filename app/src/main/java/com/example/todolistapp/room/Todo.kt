package com.example.todolistapp.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate
import java.util.Date

@Entity
data class Todo(
    @PrimaryKey(autoGenerate = true)
    val id: Int?,
    val title: String,
    val date: String,
    val description: String?
)
