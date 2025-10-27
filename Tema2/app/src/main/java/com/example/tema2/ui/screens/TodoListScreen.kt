package com.example.tema2.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tema2.data.TodoRepository
import com.example.tema2.data.TodoTask
import com.example.tema2.ui.components.TodoItem
import com.example.tema2.ui.components.AddTodoDialog
import com.example.tema2.ui.components.ModifyTodoDialog
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoListScreen(
    todoRepository: TodoRepository?,
    modifier: Modifier = Modifier
) {
    var todos by remember { mutableStateOf<List<TodoTask>>(emptyList()) }
    var showAddDialog by remember { mutableStateOf(false) }
    var showModifyDialog by remember { mutableStateOf(false) }
    var selectedTask by remember { mutableStateOf<TodoTask?>(null) }
    var isLoading by remember { mutableStateOf(true) }
    val scope = rememberCoroutineScope()

    // Load todos when screen is first displayed
    LaunchedEffect(Unit) {
        if (todoRepository != null) {
            todos = todoRepository.getAllTasks()
        } else {
            // For preview - simulate reading from txt file
            todos = listOf(
                TodoTask(1, "Task1", false),
                TodoTask(2, "Task2", false),
                TodoTask(3, "Task3", false)
            )
        }
        isLoading = false
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "My Todo List",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )
            
            FloatingActionButton(
                onClick = { showAddDialog = true },
                modifier = Modifier.size(48.dp)
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add Todo")
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Todo List
        if (isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else if (todos.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "No todos yet!\nTap the + button to add one.",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(todos) { todo ->
                    TodoItem(
                        todo = todo,
                        onToggleComplete = { taskId ->
                            if (todoRepository != null) {
                                scope.launch {
                                    todoRepository.toggleTaskCompletion(taskId)
                                    todos = todoRepository.getAllTasks()
                                }
                            } else {
                                todos = todos.map { if (it.id == taskId) it.copy(isCompleted = !it.isCompleted) else it }
                            }
                        },
                        onModify = { taskId ->
                            selectedTask = todos.find { it.id == taskId }
                            showModifyDialog = true
                        },
                        onDelete = { taskId ->
                            if (todoRepository != null) {
                                scope.launch {
                                    todoRepository.deleteTask(taskId)
                                    todos = todoRepository.getAllTasks()
                                }
                            } else {
                                todos = todos.filter { it.id != taskId }
                            }
                        }
                    )
                }
            }
        }
    }

    if (showAddDialog) {
        AddTodoDialog(
            onDismiss = { showAddDialog = false },
            onAddTodo = { title ->
                if (todoRepository != null) {
                    scope.launch {
                        todoRepository.addTask(title)
                        todos = todoRepository.getAllTasks()
                        showAddDialog = false
                    }
                } else {
                    val newId = (todos.maxOfOrNull { it.id } ?: 0) + 1
                    todos = todos + TodoTask(newId, title)
                    showAddDialog = false
                }
            }
        )
    }

    // Modify Todo Dialog
    if (showModifyDialog && selectedTask != null) {
        ModifyTodoDialog(
            currentTitle = selectedTask!!.title,
            onDismiss = { 
                showModifyDialog = false
                selectedTask = null
            },
            onModifyTodo = { newTitle ->
                if (todoRepository != null) {
                    scope.launch {
                        val updatedTask = selectedTask!!.copy(title = newTitle)
                        todoRepository.updateTask(updatedTask)
                        todos = todoRepository.getAllTasks()
                        showModifyDialog = false
                        selectedTask = null
                    }
                } else {
                    todos = todos.map { if (it.id == selectedTask!!.id) it.copy(title = newTitle) else it }
                    showModifyDialog = false
                    selectedTask = null
                }
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TodoListScreenPreview() {
    MaterialTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "My Todo List",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold
                )
                
                FloatingActionButton(
                    onClick = { },
                    modifier = Modifier.size(48.dp)
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Add Todo")
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(listOf(
                    TodoTask(1, "Task1", false),
                    TodoTask(2, "Task2", false),
                    TodoTask(3, "Task3", false)
                )) { todo ->
                    TodoItem(
                        todo = todo,
                        onToggleComplete = { },
                        onModify = { },
                        onDelete = { }
                    )
                }
            }
        }
    }
}

