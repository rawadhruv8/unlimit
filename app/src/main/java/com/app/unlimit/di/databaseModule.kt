package com.app.unlimit.di

import androidx.room.Room
import com.app.unlimit.db.LocalDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val  databaseModule = module {
    single {
        Room.databaseBuilder(
            androidContext(),
            LocalDatabase::class.java,
            "unlimit"
        ).build()
    }


    single {
        val database = get<LocalDatabase>()

        database.jokesDao()
    }
}