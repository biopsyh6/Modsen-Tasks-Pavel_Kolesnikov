package com.example.modsen_tasks_pavel_kolesnikov.ui.state

import com.example.modsen_tasks_pavel_kolesnikov.ui.model.PostUIModel

data class PostsState(
    val uiModel: PostsUIModel = PostsUIModel()
)

data class PostsUIModel(
    val posts: List<PostUIModel> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
)
