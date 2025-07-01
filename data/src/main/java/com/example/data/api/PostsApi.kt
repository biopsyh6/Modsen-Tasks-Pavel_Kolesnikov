package com.example.data.api

import com.example.data.model.CommentApiModel
import com.example.data.model.PostApiModel
import retrofit2.http.GET
import retrofit2.http.Query

interface PostsApi {
    @GET("posts")
    suspend fun getPosts(): List<PostApiModel>

    @GET("comments")
    suspend fun getComments(@Query("postId") postId: Int): List<CommentApiModel>
}