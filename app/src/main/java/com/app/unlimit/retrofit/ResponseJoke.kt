package com.app.unlimit.retrofit

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ResponseJoke(val joke: String) {

    @PrimaryKey(autoGenerate = true)
    var _id : Int?= null
}