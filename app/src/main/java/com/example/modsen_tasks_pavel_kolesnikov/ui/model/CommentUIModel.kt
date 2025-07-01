package com.example.modsen_tasks_pavel_kolesnikov.ui.model

data class CommentUIModel(
    val id: Int,
    val name: String,
    val email: String,
    val body: String,
)

val CommentUIModelMock = CommentUIModel(
    id = 1,
    name = "Sample Comment",
    email = "user@example.com",
    body = "This is a sample comment body.",
)

