package com.example.data.api

import com.example.data.model.PostApiModel
import retrofit2.http.GET

interface PostsApi {
    @GET("posts")
    suspend fun getPosts(): List<PostApiModel>
}