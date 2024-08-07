package com.example.pizza_shift_2024.catalog.domain.data

import com.example.pizza_shift_2024.catalog.domain.models.PizzaInformation
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

const val BASE_URL = "https://shift-backend.onrender.com/"
const val TIME_WAIT = 10L

val pizzaRepository = module {
    single<Retrofit> {
        Retrofit.Builder()
            .client(get())
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).build()
    }

    single<OkHttpClient> {
        OkHttpClient().newBuilder()
            .connectTimeout(TIME_WAIT, TimeUnit.SECONDS)
            .writeTimeout(TIME_WAIT, TimeUnit.SECONDS)
            .readTimeout(TIME_WAIT, TimeUnit.SECONDS)
            .addInterceptor(get<Interceptor>())
            .build()
    }

    single<HttpLoggingInterceptor> {
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

}

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

    private val pizzaAPI = retrofit.create(PizzaAPI::class.java)
    suspend fun getPizza() : PizzaInformation = pizzaAPI.getPizza()

}