package com.example.modsen_tasks_pavel_kolesnikov.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.TResult
import com.example.domain.model.exception.parseToString
import com.example.domain.usecase.GetPostsUseCase
import com.example.modsen_tasks_pavel_kolesnikov.ui.SingleFlowEvent
import com.example.modsen_tasks_pavel_kolesnikov.ui.event.PostsEvent
import com.example.modsen_tasks_pavel_kolesnikov.ui.intent.PostsIntent
import com.example.modsen_tasks_pavel_kolesnikov.ui.model.PostUIModel
import com.example.modsen_tasks_pavel_kolesnikov.ui.state.PostsState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PostsViewModel(
    private val getPostsUseCase: GetPostsUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(PostsState())
    val state = _state.asStateFlow()

    private val _event = SingleFlowEvent<PostsEvent>(viewModelScope)
    val event = _event.flow

    init {
        onIntent(PostsIntent.LoadPosts)
    }

    fun onIntent(intent: PostsIntent) {
        when (intent) {
            is PostsIntent.LoadPosts -> {
                _state.update { it.copy(uiModel = it.uiModel.copy(isLoading = true)) }
                viewModelScope.launch {
                    when(val result = getPostsUseCase()) {
                        is TResult.Error -> {
                            val errorMessage = result.exception.parseToString()
                            _state.update {
                                it.copy(uiModel = it.uiModel.copy(isLoading = false, error = errorMessage))
                            }
                            _event.emit(PostsEvent.ShowError(errorMessage))
                        }
                        is TResult.Success -> {
                            val uiPosts = result.data.map { post ->
                                PostUIModel(id = post.id, title = post.title, body = post.body)
                            }
                            _state.update {
                                it.copy(uiModel = it.uiModel.copy(posts = uiPosts, isLoading = false, error = null))
                            }
                        }
                    }
                }
            }
        }
    }
}