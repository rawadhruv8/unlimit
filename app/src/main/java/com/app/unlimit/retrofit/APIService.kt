package com.app.unlimit.retrofit

import retrofit2.http.*

import retrofit2.Response

interface APIService {

    @GET("api?format=json")
    suspend fun getJokes(): Response<ResponseJoke>

}