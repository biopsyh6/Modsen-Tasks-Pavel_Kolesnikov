package com.example.domain.usecase

import com.example.domain.model.LoginDomainModel
import com.example.domain.repository.ILoginRepository

class LoginUseCase(
    private val loginRepository: ILoginRepository
) {
    suspend operator fun invoke(login: String, password: String): Result<Unit> {
        val model = LoginDomainModel(login, password)
        return loginRepository.login(model)
    }
}