package com.example.tema2.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun ModifyTodoDialog(
    currentTitle: String,
    onDismiss: () -> Unit,
    onModifyTodo: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var title by remember { mutableStateOf(TextFieldValue(currentTitle)) }
    var isTitleEmpty by remember { mutableStateOf(false) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text("Modify Todo")
        },
        text = {
            OutlinedTextField(
                value = title,
                onValueChange = { newValue ->
                    title = newValue
                    isTitleEmpty = false
                },
                label = { Text("Title") },
                isError = isTitleEmpty,
                supportingText = if (isTitleEmpty) { 
                    { Text("Title is required") } 
                } else null,
                modifier = Modifier.fillMaxWidth()
            )
        },
        confirmButton = {
            TextButton(
                onClick = {
                    if (title.text.isNotBlank()) {
                        onModifyTodo(title.text.trim())
                    } else {
                        isTitleEmpty = true
                    }
                }
            ) {
                Text("Update")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun ModifyTodoDialogPreview() {
    MaterialTheme {
        ModifyTodoDialog(
            currentTitle = "Task1",
            onDismiss = {},
            onModifyTodo = { _ -> }
        )
    }
}
