package com.example.modsen_tasks_pavel_kolesnikov.di

import com.example.modsen_tasks_pavel_kolesnikov.ui.viewmodel.LoginViewModel
import com.example.modsen_tasks_pavel_kolesnikov.ui.viewmodel.PostsViewModel
import com.example.modsen_tasks_pavel_kolesnikov.ui.viewmodel.TaskListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel<LoginViewModel> { LoginViewModel(loginUseCase = get()) }
    viewModel<TaskListViewModel> { TaskListViewModel() }
    viewModel<PostsViewModel> { PostsViewModel(getPostsUseCase = get()) }
}