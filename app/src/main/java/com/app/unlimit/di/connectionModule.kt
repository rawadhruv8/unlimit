package com.app.unlimit.di

import android.content.Context
import android.net.ConnectivityManager
import com.app.unlimit.util.ConnectionManager
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val connectionModule = module {
    single {
        ConnectionManager()
    }

    single {
        (androidContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager)
    }
}