package com.example.pizza_shift_2024.domain.data

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class PizzaRepository {
    private companion object {
        const val BASE_URL = "https://shift-backend.onrender.com/"
        const val TIME_OUT = 10L
    }

    val retrofit = Retrofit.Builder()
        .client(provideOkHttpClient())
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create()).build()

    private fun provideOkHttpClient() : OkHttpClient = OkHttpClient().newBuilder()
        .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
        .writeTimeout(TIME_OUT, TimeUnit.SECONDS)
        .readTimeout(TIME_OUT, TimeUnit.SECONDS)
        .addInterceptor(provideLoggingInterceptor())
        .build()

    private fun provideLoggingInterceptor() = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }
}