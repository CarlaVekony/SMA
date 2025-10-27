# Todo List App

A simple Android todo list application built with Jetpack Compose that mimics a database using a text file for data persistence.

## Features

- ✅ **View all tasks** - Display all todo items in a clean, modern interface
- ➕ **Add new tasks** - Create new todos with title and optional description
- ☑️ **Mark as complete** - Toggle task completion status with checkboxes
- 🗑️ **Delete tasks** - Remove tasks you no longer need
- 💾 **Persistent storage** - Data is saved to a local text file (todo_tasks.txt)

## How it works

The app uses a file-based repository (`TodoRepository`) that mimics a database by:
- Storing tasks in a simple text file format
- Loading tasks from the file on app startup
- Saving changes immediately when tasks are modified
- Using coroutines for background file operations

## Data Format

Tasks are stored in `todo_tasks.txt` with the format:
```
id|title|description|isCompleted|createdAt
```

## CRUD Operations

- **Create**: Add new tasks via the floating action button
- **Read**: Display all tasks in a scrollable list
- **Update**: Toggle completion status by tapping checkboxes
- **Delete**: Remove tasks using the delete button

## UI Components

- **TodoListScreen**: Main screen displaying the list of tasks
- **TodoItem**: Individual task component with checkbox and delete button
- **AddTodoDialog**: Dialog for adding new tasks

## Getting Started

1. Open the project in Android Studio
2. Build and run the app on an emulator or device
3. The app will initialize with 5 sample tasks
4. Use the + button to add new tasks
5. Tap checkboxes to mark tasks as complete
6. Use the delete button to remove tasks

The app follows Material Design 3 principles and provides a clean, intuitive user experience similar to the Android Basics with Compose course examples.
