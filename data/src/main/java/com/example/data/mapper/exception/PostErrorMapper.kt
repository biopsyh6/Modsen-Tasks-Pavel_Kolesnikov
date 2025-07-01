package com.example.data.mapper.exception

import com.example.domain.model.exception.PostExceptionDomainModel
import retrofit2.HttpException
import java.net.ConnectException
import java.net.UnknownHostException

fun Throwable.toPostExceptionDomainModel(): PostExceptionDomainModel {
    return when (this) {
        is UnknownHostException, is ConnectException -> PostExceptionDomainModel.NoInternet(this)
        is HttpException -> when (code()) {
            in 500..526 -> PostExceptionDomainModel.ServerError(this)
            else -> PostExceptionDomainModel.Other(this)
        }
        is PostExceptionDomainModel -> this
        else -> PostExceptionDomainModel.Other(this)
    }
}