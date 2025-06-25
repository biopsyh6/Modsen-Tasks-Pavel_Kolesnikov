package com.example.data.mapper

import com.example.data.model.PostApiModel
import com.example.domain.model.PostDomainModel

fun PostApiModel.toDomainModel(): PostDomainModel = PostDomainModel(
    id = id,
    userId = userId,
    title = title,
    body = body
)