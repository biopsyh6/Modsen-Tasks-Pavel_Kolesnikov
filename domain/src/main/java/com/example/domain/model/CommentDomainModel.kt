package com.example.domain.model

data class CommentDomainModel(
    val id: Int,
    val postId: Int,
    val name: String,
    val email: String,
    val body: String,
)
