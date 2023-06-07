package com.app.unlimit.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.app.unlimit.retrofit.ResponseJoke

@Database(entities = [ResponseJoke::class],version = 1 , exportSchema = false)
abstract class LocalDatabase : RoomDatabase(){

    abstract fun jokesDao() : JokesDao
}