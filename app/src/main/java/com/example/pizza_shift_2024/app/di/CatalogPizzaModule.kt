package com.example.pizza_shift_2024.app.di

import com.example.pizza_shift_2024.catalog.domain.data.PizzaAPI
import com.example.pizza_shift_2024.app.presentaion.usecase.PizzaViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

val catalogPizzaModule = module {
    single { get<Retrofit>().create(PizzaAPI::class.java) }


    viewModel {
        PizzaViewModel(get())
    }


}