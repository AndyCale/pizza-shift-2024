package com.example.pizza_shift_2024.data

import retrofit2.http.GET

interface PizzaAPI {
    @GET("pizza/catalog")
    suspend fun getPizza(): PizzaInformation
}