package com.example.modsen_tasks_pavel_kolesnikov.ui.event

sealed interface LoginEvent {
    data class ShowError(val error: String) : LoginEvent
    data object NavigateToSuccessScreen: LoginEvent
}