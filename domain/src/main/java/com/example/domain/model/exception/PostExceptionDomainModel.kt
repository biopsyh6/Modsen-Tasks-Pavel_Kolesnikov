package com.example.domain.model.exception

sealed class PostExceptionDomainModel(exception: Throwable) : Throwable(exception) {
    override val cause: Throwable = exception

    data class NoInternet(val exception: Throwable) : PostExceptionDomainModel(exception)
    data class ServerError(val exception: Throwable) : PostExceptionDomainModel(exception)
    data class Other(val exception: Throwable) : PostExceptionDomainModel(exception)
}

fun PostExceptionDomainModel.parseToString(): String = when (this) {
    is PostExceptionDomainModel.NoInternet -> "No internet connection"
    is PostExceptionDomainModel.Other -> "Unknown error"
    is PostExceptionDomainModel.ServerError -> "Server error occurred"
}