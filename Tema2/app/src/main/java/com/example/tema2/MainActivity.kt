package com.example.tema2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.tema2.data.TodoRepository
import com.example.tema2.ui.theme.Tema2Theme
import com.example.tema2.ui.screens.TodoListScreen

class MainActivity : ComponentActivity() {
    private lateinit var todoRepository: TodoRepository
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        
        todoRepository = TodoRepository(this)
        
        setContent {
            Tema2Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    TodoListScreen(
                        todoRepository = todoRepository,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainActivityPreview() {
    Tema2Theme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            TodoListScreen(
                todoRepository = null,
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}