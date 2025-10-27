package com.example.tema2.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun AddTodoDialog(
    onDismiss: () -> Unit,
    onAddTodo: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var title by remember { mutableStateOf(TextFieldValue("")) }
    var isTitleEmpty by remember { mutableStateOf(false) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text("Add New Todo")
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
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done)
            )
        },
        confirmButton = {
            TextButton(
                onClick = {
                    if (title.text.isNotBlank()) {
                        onAddTodo(title.text.trim())
                    } else {
                        isTitleEmpty = true
                    }
                }
            ) {
                Text("Add")
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
fun AddTodoDialogPreview() {
    MaterialTheme {
        AddTodoDialog(
            onDismiss = {},
            onAddTodo = { _ -> }
        )
    }
}