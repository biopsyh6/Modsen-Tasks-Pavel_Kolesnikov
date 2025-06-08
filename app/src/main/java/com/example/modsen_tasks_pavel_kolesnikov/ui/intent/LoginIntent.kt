package com.example.modsen_tasks_pavel_kolesnikov.ui.intent

sealed interface LoginIntent {
    data class UpdateLogin(val login: String) : LoginIntent
    data class UpdatePassword(val password: String) : LoginIntent
    data object SubmitLogin : LoginIntent
}