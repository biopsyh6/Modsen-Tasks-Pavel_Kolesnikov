package com.example.modsen_tasks_pavel_kolesnikov.di

import com.example.data.api.PostsApi
import com.example.data.repository.LoginRepositoryImpl
import com.example.data.repository.PostsRepositoryImpl
import com.example.data.source.LoginDataSource
import com.example.data.source.PostsDataSource
import com.example.domain.repository.ILoginRepository
import com.example.domain.repository.IPostsRepository
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val dataModule = module {
    single { LoginDataSource() }
    single<ILoginRepository> { LoginRepositoryImpl(dataSource = get()) }

    single { GsonBuilder().create() }
    single { OkHttpClient.Builder().build() }
    single {
        Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com/")
            .client(get())
            .addConverterFactory(GsonConverterFactory.create(get<Gson>()))
            .build()
    }
    single { get<Retrofit>().create(PostsApi::class.java) }
    single { PostsDataSource(api = get()) }
    single<IPostsRepository> { PostsRepositoryImpl(dataSource = get()) }

}