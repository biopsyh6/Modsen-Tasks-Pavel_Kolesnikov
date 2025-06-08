package com.example.data.source

import com.example.data.model.LoginDataModel
import kotlinx.coroutines.delay

class LoginDataSource {
    private val validCredentials = listOf(
        LoginDataModel("admin", "password12345"),
        LoginDataModel("user", "user456")
    )

    suspend fun attemptLogin(model: LoginDataModel): Result<Unit> {
        delay(1000)
        return if (validCredentials.contains(model)) {
            Result.success(Unit)
        } else {
            Result.failure(Exception("Invalid login or password"))
        }
    }
}