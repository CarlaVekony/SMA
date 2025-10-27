package com.example.tema2.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tema2.data.TodoTask
import java.io.File

@Composable
fun TodoItem(
    todo: TodoTask,
    onToggleComplete: (Int) -> Unit,
    onModify: (Int) -> Unit,
    onDelete: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Checkbox
            Checkbox(
                checked = todo.isCompleted,
                onCheckedChange = { onToggleComplete(todo.id) }
            )
            
            Spacer(modifier = Modifier.width(12.dp))
            
            // Task content
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = todo.title,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Medium,
                    textDecoration = if (todo.isCompleted) TextDecoration.LineThrough else null,
                    color = if (todo.isCompleted) 
                        MaterialTheme.colorScheme.onSurfaceVariant 
                    else 
                        MaterialTheme.colorScheme.onSurface
                )
            }
            
            Spacer(modifier = Modifier.width(8.dp))
            
            // Modify button
            IconButton(
                onClick = { onModify(todo.id) }
            ) {
                Icon(
                    Icons.Default.Edit,
                    contentDescription = "Modify",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
            
            // Delete button
            IconButton(
                onClick = { onDelete(todo.id) }
            ) {
                Icon(
                    Icons.Default.Delete,
                    contentDescription = "Delete",
                    tint = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TodoItemPreview() {
    MaterialTheme {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Read from txt file for preview
            val file = File("D:/Anul_4_semestru_1/SMA/todo_tasks.txt")
            val tasks = if (file.exists()) {
                try {
                    file.readLines().mapNotNull { line ->
                        val parts = line.split("|")
                        if (parts.size >= 3) {
                            TodoTask(
                                id = parts[0].toIntOrNull() ?: 0,
                                title = parts[1],
                                isCompleted = parts[2].toBoolean()
                            )
                        } else null
                    }
                } catch (e: Exception) {
                    emptyList()
                }
            } else {
                emptyList()
            }
            
            tasks.forEach { task ->
                TodoItem(
                    todo = task,
                    onToggleComplete = {},
                    onModify = {},
                    onDelete = {}
                )
            }
        }
    }
}
