package com.example.modsen_tasks_pavel_kolesnikov.ui.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.TResult
import com.example.domain.model.exception.parseToString
import com.example.domain.usecase.GetCommentsUseCase
import com.example.modsen_tasks_pavel_kolesnikov.ui.SingleFlowEvent
import com.example.modsen_tasks_pavel_kolesnikov.ui.event.CommentsEvent
import com.example.modsen_tasks_pavel_kolesnikov.ui.intent.CommentsIntent
import com.example.modsen_tasks_pavel_kolesnikov.ui.model.CommentUIModel
import com.example.modsen_tasks_pavel_kolesnikov.ui.model.PostUIModel
import com.example.modsen_tasks_pavel_kolesnikov.ui.state.CommentsState
import com.example.modsen_tasks_pavel_kolesnikov.ui.state.CommentsUIModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json

class CommentsViewModel(
    private val getCommentsUseCase: GetCommentsUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _state = MutableStateFlow(CommentsState())
    val state = _state.asStateFlow()

    private val _event = SingleFlowEvent<CommentsEvent>(viewModelScope)
    val event = _event.flow

    init {
        val postId: Int = savedStateHandle.get<Int>("postId") ?: -1
        val postJson: String? = savedStateHandle.get<String>("post")
        val post = postJson?.let {
            try {
                Json.decodeFromString<PostUIModel>(it)
            } catch (e: Exception) {
                null
            }
        } ?: PostUIModel(id = postId, title = "Loading Post...", body = "Please wait")

        _state.update { it.copy(uiModel = CommentsUIModel(post = post)) }
        onIntent(CommentsIntent.LoadComments)
    }

    fun onIntent(intent: CommentsIntent) {
        when (intent) {
            is CommentsIntent.LoadComments -> {
                _state.update { it.copy(uiModel = it.uiModel.copy(isLoading = true)) }
                viewModelScope.launch {
                    when (val result =
                        getCommentsUseCase(postId = _state.value.uiModel.post!!.id)) {
                        is TResult.Error -> {
                            val errorMessage = result.exception.parseToString()
                            _state.update {
                                it.copy(
                                    uiModel = it.uiModel.copy(
                                        isLoading = false,
                                        error = errorMessage
                                    )
                                )
                            }
                            _event.emit(CommentsEvent.ShowError(errorMessage))
                        }

                        is TResult.Success -> {
                            val uiComments = result.data.map { comment ->
                                CommentUIModel(
                                    id = comment.id,
                                    name = comment.name,
                                    email = comment.email,
                                    body = comment.body,
                                )
                            }
                            _state.update {
                                it.copy(
                                    uiModel = it.uiModel.copy(
                                        comments = uiComments,
                                        isLoading = false,
                                        error = null
                                    )
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}