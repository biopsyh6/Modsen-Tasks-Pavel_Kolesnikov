package com.example.modsen_tasks_pavel_kolesnikov.di

import com.example.domain.usecase.GetCommentsUseCase
import com.example.domain.usecase.GetPostsUseCase
import com.example.domain.usecase.LoginUseCase
import org.koin.dsl.module

val domainModule = module {
    factory<LoginUseCase> { LoginUseCase(loginRepository = get()) }
    factory<GetPostsUseCase> { GetPostsUseCase(postsRepository = get()) }
    factory<GetCommentsUseCase> { GetCommentsUseCase(commentsRepository = get()) }
}