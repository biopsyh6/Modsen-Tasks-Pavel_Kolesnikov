package com.example.data.model

import com.google.gson.annotations.SerializedName

data class CommentApiModel(
    @SerializedName("id")
    val id: Int,
    @SerializedName("postId")
    val postId: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("body")
    val body: String,
)
