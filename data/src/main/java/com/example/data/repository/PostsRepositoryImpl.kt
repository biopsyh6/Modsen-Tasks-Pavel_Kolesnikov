package com.example.data.repository

import com.example.data.mapper.exception.toPostExceptionDomainModel
import com.example.data.mapper.toDomainModel
import com.example.data.source.PostsDataSource
import com.example.domain.TResult
import com.example.domain.model.PostDomainModel
import com.example.domain.model.exception.PostExceptionDomainModel
import com.example.domain.repository.IPostsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PostsRepositoryImpl(
    private val dataSource: PostsDataSource
) : IPostsRepository {
    override suspend fun getPosts(): TResult<List<PostDomainModel>, PostExceptionDomainModel> = withContext(Dispatchers.IO) {
        runCatching {
            val apiPosts = dataSource.fetchPosts()
            val domainPosts = apiPosts.map { it.toDomainModel() }
            TResult.Success<List<PostDomainModel>, PostExceptionDomainModel>(domainPosts)
        }.getOrElse {
            TResult.Error(it.toPostExceptionDomainModel())
        }
    }
}