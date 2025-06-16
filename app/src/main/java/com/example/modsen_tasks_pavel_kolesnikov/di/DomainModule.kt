package com.example.modsen_tasks_pavel_kolesnikov.di

import com.example.domain.usecase.LoginUseCase
import org.koin.dsl.module

val domainModule = module {
    factory<LoginUseCase> { LoginUseCase(loginRepository = get()) }
}