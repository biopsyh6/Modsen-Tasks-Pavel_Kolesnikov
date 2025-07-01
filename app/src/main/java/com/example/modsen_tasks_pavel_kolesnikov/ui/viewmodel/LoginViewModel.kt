package com.example.modsen_tasks_pavel_kolesnikov.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.usecase.LoginUseCase
import com.example.modsen_tasks_pavel_kolesnikov.ui.SingleFlowEvent
import com.example.modsen_tasks_pavel_kolesnikov.ui.event.LoginEvent
import com.example.modsen_tasks_pavel_kolesnikov.ui.intent.LoginIntent
import com.example.modsen_tasks_pavel_kolesnikov.ui.state.LoginState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LoginViewModel(
    private val loginUseCase: LoginUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(LoginState())
    val state: StateFlow<LoginState> = _state.asStateFlow()

    private val _event = SingleFlowEvent<LoginEvent>(viewModelScope)
    val event = _event.flow

    fun onIntent(intent: LoginIntent) {
        when (intent) {
            is LoginIntent.UpdateLogin -> {
                _state.value = _state.value.copy(
                    uiModel = _state.value.uiModel.copy(login = intent.login)
                )
            }

            is LoginIntent.UpdatePassword -> {
                _state.value = _state.value.copy(
                    uiModel = _state.value.uiModel.copy(password = intent.password)
                )
            }

            is LoginIntent.SubmitLogin -> {
                _state.value = _state.value.copy(
                    uiModel = _state.value.uiModel.copy(isLoading = true)
                )
                viewModelScope.launch {
                    val result = loginUseCase(
                        login = _state.value.uiModel.login,
                        password = _state.value.uiModel.password
                    )
                    _state.value = _state.value.copy(
                        uiModel = _state.value.uiModel.copy(isLoading = false)
                    )
                    if (result.isSuccess) {
                        _event.emit(LoginEvent.NavigateToSuccessScreen)
                    } else {
                        _event.emit(
                            LoginEvent
                                .ShowError(result.exceptionOrNull()?.message ?: "Unknown error")
                        )
                    }
                }
            }
        }
    }
}