package com.example.modsen_tasks_pavel_kolesnikov.ui.event

sealed interface PostsEvent {
    data class ShowError(val message: String) : PostsEvent
}