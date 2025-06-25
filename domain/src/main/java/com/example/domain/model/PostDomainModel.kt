package com.example.domain.model

data class PostDomainModel(
    val id: Int,
    val userId: Int,
    val title: String,
    val body: String,
)