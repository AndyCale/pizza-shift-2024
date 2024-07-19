package com.example.pizza_shift_2024.data

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class PizzaRepository {
    val baseUrl = "https://shift-backend.onrender.com/"
    val timeOut = 10L

    val retrofit = Retrofit.Builder()
        .client(provideOkHttpClient())
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create()).build()

    private fun provideOkHttpClient() : OkHttpClient = OkHttpClient().newBuilder()
        .connectTimeout(timeOut, TimeUnit.SECONDS)
        .writeTimeout(timeOut, TimeUnit.SECONDS)
        .readTimeout(timeOut, TimeUnit.SECONDS)
        .addInterceptor(provideLoggingInterceptor())
        .build()

    private fun provideLoggingInterceptor() = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }


    //val pizzaAPI = retrofit.create(PizzaAPI::class.java)
}