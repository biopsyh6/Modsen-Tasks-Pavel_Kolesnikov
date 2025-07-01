package com.example.modsen_tasks_pavel_kolesnikov.ui.intent


sealed interface CommentsIntent {
    data object LoadComments : CommentsIntent
}