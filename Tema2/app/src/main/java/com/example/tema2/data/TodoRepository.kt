package com.example.tema2.data

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileWriter
import java.io.FileReader
import java.io.BufferedReader
import java.io.IOException

class TodoRepository(private val context: Context) {
    private val fileName = "todo_tasks.txt"
    private val file: File by lazy {
        File("D:/Anul_4_semestru_1/SMA", fileName)
    }
    
    private var nextId = 1
    
    init {
        // Initialize with some dummy data if file doesn't exist
        if (!file.exists()) {
            initializeDummyData()
        } else {
            // Find the highest ID from existing data
            loadTasks().forEach { task ->
                if (task.id >= nextId) {
                    nextId = task.id + 1
                }
            }
        }
    }
    
    private fun initializeDummyData() {
        // Create empty file - no hardcoded tasks
        saveTasks(emptyList())
        nextId = 1
    }
    
    suspend fun getAllTasks(): List<TodoTask> = withContext(Dispatchers.IO) {
        loadTasks()
    }
    
    suspend fun getTaskById(id: Int): TodoTask? = withContext(Dispatchers.IO) {
        loadTasks().find { it.id == id }
    }
    
    suspend fun addTask(title: String): TodoTask = withContext(Dispatchers.IO) {
        val newTask = TodoTask(nextId++, title)
        val tasks = loadTasks().toMutableList()
        tasks.add(newTask)
        saveTasks(tasks)
        newTask
    }
    
    suspend fun updateTask(task: TodoTask): Boolean = withContext(Dispatchers.IO) {
        val tasks = loadTasks().toMutableList()
        val index = tasks.indexOfFirst { it.id == task.id }
        if (index != -1) {
            tasks[index] = task
            saveTasks(tasks)
            true
        } else {
            false
        }
    }
    
    suspend fun deleteTask(id: Int): Boolean = withContext(Dispatchers.IO) {
        val tasks = loadTasks().toMutableList()
        val removed = tasks.removeAll { it.id == id }
        if (removed) {
            saveTasks(tasks)
        }
        removed
    }
    
    suspend fun toggleTaskCompletion(id: Int): Boolean = withContext(Dispatchers.IO) {
        val task = getTaskById(id)
        if (task != null) {
            val updatedTask = task.copy(isCompleted = !task.isCompleted)
            updateTask(updatedTask)
        } else {
            false
        }
    }
    
    private fun loadTasks(): List<TodoTask> {
        return try {
            if (!file.exists()) {
                return emptyList()
            }
            
            val tasks = mutableListOf<TodoTask>()
            BufferedReader(FileReader(file)).use { reader ->
                var line: String?
                while (reader.readLine().also { line = it } != null) {
                    val parts = line!!.split("|")
                    if (parts.size >= 3) {
                        val task = TodoTask(
                            id = parts[0].toIntOrNull() ?: 0,
                            title = parts[1],
                            isCompleted = parts[2].toBoolean()
                        )
                        tasks.add(task)
                    }
                }
            }
            tasks
        } catch (e: IOException) {
            e.printStackTrace()
            emptyList()
        }
    }
    
    private fun saveTasks(tasks: List<TodoTask>) {
        try {
            FileWriter(file).use { writer ->
                tasks.forEach { task ->
                    writer.write("${task.id}|${task.title}|${task.isCompleted}\n")
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}
