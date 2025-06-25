package com.example.modsen_tasks_pavel_kolesnikov.ui.intent

sealed interface PostsIntent {
    data object LoadPosts : PostsIntent
}