package com.example.data.repository

import com.example.data.mapper.toEntity
import com.example.data.source.LoginDataSource
import com.example.domain.model.LoginDomainModel
import com.example.domain.repository.ILoginRepository

class LoginRepositoryImpl(
    private val dataSource: LoginDataSource
) : ILoginRepository {
    override suspend fun login(login: LoginDomainModel): Result<Unit> {
        return dataSource.attemptLogin(login.toEntity())
    }
}