package com.app.unlimit.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.app.unlimit.retrofit.ResponseJoke

@Dao
interface JokesDao {

    @Insert
    fun insertData(data : ResponseJoke)

    @Query("SELECT * FROM ResponseJoke order by _id DESC limit 10")
    fun getJokesList() : List<ResponseJoke>

}