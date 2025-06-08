package com.example.modsen_tasks_pavel_kolesnikov.di

import com.example.data.repository.LoginRepositoryImpl
import com.example.data.source.LoginDataSource
import com.example.domain.repository.ILoginRepository
import org.koin.dsl.module

val dataModule = module {
    single { LoginDataSource() }
    single<ILoginRepository> { LoginRepositoryImpl(dataSource = get()) }
}