package com.example.data.source

import com.example.data.api.PostsApi
import com.example.data.model.CommentApiModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CommentsDataSource(
    private val api: PostsApi
) {
    suspend fun fetchComments(postId: Int): List<CommentApiModel> = withContext(Dispatchers.IO) {
        api.getComments(postId)
    }
}