package com.example.data.mapper

import com.example.data.model.CommentApiModel
import com.example.domain.model.CommentDomainModel

fun CommentApiModel.toDomainModel(): CommentDomainModel = CommentDomainModel(
    id = id,
    postId = postId,
    name = name,
    email = email,
    body = body
)