package com.app.unlimit.di

import com.app.unlimit.retrofit.APIService
import com.google.gson.GsonBuilder
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


val networkModule = module {

    single {
        createBasicAuthService()
    }
}

const val BASE_URL = "https://geek-jokes.sameerkumar.website/"

private fun httpInterceptor() = HttpLoggingInterceptor().apply {
    level = HttpLoggingInterceptor.Level.BODY
}

private fun basicOkHttpClient() = OkHttpClient.Builder().addInterceptor(httpInterceptor())


fun createBasicAuthService(): APIService {

    val gson = GsonBuilder()
        .setLenient()
        .create()

    val retrofit = Retrofit.Builder()
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create(gson)).client(basicOkHttpClient().build())
        .baseUrl(BASE_URL)
        .build()
    return retrofit.create(APIService::class.java);
}