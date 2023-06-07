package com.app.unlimit.di

import com.app.unlimit.JokeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    viewModel { JokeViewModel(get(), get()) }

}