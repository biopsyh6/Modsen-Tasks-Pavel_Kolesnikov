package com.example.domain.repository

import com.example.domain.model.LoginDomainModel

interface ILoginRepository {
    suspend fun login(login: LoginDomainModel): Result<Unit>
}