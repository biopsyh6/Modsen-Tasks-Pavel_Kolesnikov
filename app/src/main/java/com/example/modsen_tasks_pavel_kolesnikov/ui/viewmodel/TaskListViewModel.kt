package com.example.modsen_tasks_pavel_kolesnikov.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.modsen_tasks_pavel_kolesnikov.ui.SingleFlowEvent
import com.example.modsen_tasks_pavel_kolesnikov.ui.event.TaskListEvent
import com.example.modsen_tasks_pavel_kolesnikov.ui.intent.TaskListIntent
import com.example.modsen_tasks_pavel_kolesnikov.ui.state.TaskListState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class TaskListViewModel : ViewModel() {
    private val _state = MutableStateFlow(TaskListState())
    val state: StateFlow<TaskListState> = _state.asStateFlow()

    private val _event = SingleFlowEvent<TaskListEvent>(viewModelScope)
    val event = _event.flow

    fun onIntent(intent: TaskListIntent) {
        when (intent) {
            is TaskListIntent.SelectTask -> {
                _event.emit(TaskListEvent.NavigateToTask(intent.task))
            }
        }
    }
}