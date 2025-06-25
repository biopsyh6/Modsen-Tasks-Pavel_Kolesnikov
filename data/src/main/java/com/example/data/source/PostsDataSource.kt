package com.example.data.source

import com.example.data.api.PostsApi
import com.example.data.model.PostApiModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PostsDataSource(
    private val api: PostsApi
) {
    suspend fun fetchPosts(): List<PostApiModel> = withContext(Dispatchers.IO) {
        api.getPosts()
    }
}