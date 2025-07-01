package com.example.domain.usecase

import com.example.domain.TResult
import com.example.domain.model.CommentDomainModel
import com.example.domain.model.exception.PostExceptionDomainModel
import com.example.domain.repository.ICommentsRepository

class GetCommentsUseCase(
    private val commentsRepository: ICommentsRepository
) {
    suspend operator fun invoke(postId: Int): TResult<List<CommentDomainModel>, PostExceptionDomainModel> {
        return commentsRepository.getComments(postId)
    }
}