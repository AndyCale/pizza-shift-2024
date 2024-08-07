package com.example.pizza_shift_2024.feature.catalog.domain.data

import com.example.pizza_shift_2024.feature.catalog.domain.models.PizzaInformation
import retrofit2.http.GET

interface PizzaAPI {
    @GET("pizza/catalog")
    suspend fun getPizza(): PizzaInformation
}