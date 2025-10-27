package com.example.tema2.data

data class TodoTask(
    val id: Int,
    val title: String,
    val isCompleted: Boolean = false
)
