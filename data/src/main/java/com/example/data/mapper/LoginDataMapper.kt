package com.example.data.mapper

import com.example.data.model.LoginDataModel
import com.example.domain.model.LoginDomainModel

fun LoginDataModel.toModel() = LoginDomainModel(
    login = login,
    password = password,
)

fun LoginDomainModel.toEntity() = LoginDataModel(
    login = login,
    password = password,
)