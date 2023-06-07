package com.app.unlimit

import android.app.Application
import com.app.unlimit.di.connectionModule
import com.app.unlimit.di.databaseModule
import com.app.unlimit.di.networkModule
import com.app.unlimit.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin


class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(applicationContext)
            modules(listOf(networkModule, connectionModule, viewModelModule, databaseModule))
        }
    }
}