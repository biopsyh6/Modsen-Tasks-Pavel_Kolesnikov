package com.example.modsen_tasks_pavel_kolesnikov.ui.event

sealed interface CommentsEvent {
    data class ShowError(val message: String) : CommentsEvent
}