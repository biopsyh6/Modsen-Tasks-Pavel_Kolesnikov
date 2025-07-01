package com.example.modsen_tasks_pavel_kolesnikov.ui.model

import kotlinx.serialization.Serializable

@Serializable
data class PostUIModel(
    val id: Int,
    val title: String,
    val body: String,
)

val PostUIModelMock = PostUIModel(
    id = 1,
    title = "Sample Post Title",
    body = "This is a sample post body content"
)
