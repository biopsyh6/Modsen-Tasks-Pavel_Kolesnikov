package com.example.domain.repository

import com.example.domain.TResult
import com.example.domain.model.PostDomainModel
import com.example.domain.model.exception.PostExceptionDomainModel

interface IPostsRepository {
    suspend fun getPosts(): TResult<List<PostDomainModel>, PostExceptionDomainModel>
}