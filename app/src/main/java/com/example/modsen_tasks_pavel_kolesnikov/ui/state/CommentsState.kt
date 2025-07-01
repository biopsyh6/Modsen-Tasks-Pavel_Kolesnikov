package com.example.modsen_tasks_pavel_kolesnikov.ui.state

import com.example.modsen_tasks_pavel_kolesnikov.ui.model.CommentUIModel
import com.example.modsen_tasks_pavel_kolesnikov.ui.model.PostUIModel

data class CommentsState(
    val uiModel: CommentsUIModel = CommentsUIModel()
)

data class CommentsUIModel(
    val post: PostUIModel? = null,
    val comments: List<CommentUIModel> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
)