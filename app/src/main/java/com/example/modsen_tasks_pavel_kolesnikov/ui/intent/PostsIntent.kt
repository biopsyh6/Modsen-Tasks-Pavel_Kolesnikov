package com.example.modsen_tasks_pavel_kolesnikov.ui.intent

import com.example.modsen_tasks_pavel_kolesnikov.ui.model.PostUIModel

sealed interface PostsIntent {
    data object LoadPosts : PostsIntent
}