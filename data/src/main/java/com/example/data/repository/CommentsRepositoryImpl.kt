package com.example.data.repository

import com.example.data.mapper.exception.toPostExceptionDomainModel
import com.example.data.mapper.toDomainModel
import com.example.data.source.CommentsDataSource
import com.example.domain.TResult
import com.example.domain.model.CommentDomainModel
import com.example.domain.model.exception.PostExceptionDomainModel
import com.example.domain.repository.ICommentsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CommentsRepositoryImpl(
    private val dataSource: CommentsDataSource
) : ICommentsRepository {
    override suspend fun getComments(postId: Int): TResult<List<CommentDomainModel>, PostExceptionDomainModel> = withContext(Dispatchers.IO) {
        runCatching {
            val apiComments = dataSource.fetchComments(postId)
            val domainComments = apiComments.map { it.toDomainModel() }
            TResult.Success<List<CommentDomainModel>, PostExceptionDomainModel>(domainComments)
        }.getOrElse {
            TResult.Error(it.toPostExceptionDomainModel())
        }
    }
}