package com.example.domain.usecase

import com.example.domain.TResult
import com.example.domain.model.PostDomainModel
import com.example.domain.model.exception.PostExceptionDomainModel
import com.example.domain.repository.IPostsRepository

class GetPostsUseCase(
    private val postsRepository: IPostsRepository
) {
    suspend operator fun invoke(): TResult<List<PostDomainModel>, PostExceptionDomainModel> {
        return postsRepository.getPosts()
    }
}