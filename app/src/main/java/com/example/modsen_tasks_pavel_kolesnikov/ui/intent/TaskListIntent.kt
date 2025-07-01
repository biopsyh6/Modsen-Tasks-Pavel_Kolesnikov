package com.example.modsen_tasks_pavel_kolesnikov.ui.intent

sealed interface TaskListIntent {
    data class SelectTask(val task: String) : TaskListIntent
}