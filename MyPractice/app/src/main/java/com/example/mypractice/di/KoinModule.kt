package com.example.mypractice.di

import com.example.mypractice.model.repository.HomeRepository
import com.example.mypractice.ui.main.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val repoModule = module {
    single {
        HomeRepository()
    }
}

val viewModelModule = module {
    viewModel {
        MainViewModel(get())
    }
}

val appModule = listOf(repoModule, viewModelModule)