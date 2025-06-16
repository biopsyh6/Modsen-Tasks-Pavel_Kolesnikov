package com.example.modsen_tasks_pavel_kolesnikov.ui.event

sealed interface TaskListEvent {
    data class NavigateToTask(val task: String) : TaskListEvent
}