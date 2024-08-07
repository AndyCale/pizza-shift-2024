package com.example.pizza_shift_2024.feature.catalog.domain.data

import com.example.pizza_shift_2024.app.presentaion.usecase.PizzaViewModel
import com.example.pizza_shift_2024.feature.catalog.domain.models.Pizza
import com.example.pizza_shift_2024.feature.catalog.domain.models.PizzaInformation
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import org.koin.java.KoinJavaComponent.get
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.dsl.singleOf
import org.koin.java.KoinJavaComponent.inject
import org.koin.ktor.ext.inject

const val BASE_URL = "https://shift-backend.onrender.com/"
const val TIME_WAIT = 10L

val pizzaRepository = module {
    single<PizzaRepository> { PizzaRepository(get<PizzaAPI>()) }

    single<PizzaAPI> { get<Retrofit>().create(PizzaAPI::class.java) }

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
            .addInterceptor(get<HttpLoggingInterceptor>())
            .build()
    }

    single<HttpLoggingInterceptor> {
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }
}

class PizzaRepository(private val pizzaApi : PizzaAPI) {
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

    suspend fun getPizza() : PizzaInformation = pizzaApi.getPizza()

}