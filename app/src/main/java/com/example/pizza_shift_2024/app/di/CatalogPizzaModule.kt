package com.example.pizza_shift_2024.app.di

import com.example.pizza_shift_2024.feature.catalog.domain.data.PizzaAPI
import com.example.pizza_shift_2024.app.presentaion.usecase.PizzaViewModel
import com.example.pizza_shift_2024.feature.catalog.adapter.PizzaAdapter
import com.example.pizza_shift_2024.feature.catalog.domain.data.PizzaRepository
import com.example.pizza_shift_2024.feature.catalog.domain.models.Pizza
import com.example.pizza_shift_2024.feature.catalog.usecase.CreatorListRecyclerView
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import retrofit2.Retrofit

val catalogPizzaModule = module {
    viewModel<PizzaViewModel> {
        PizzaViewModel(get<PizzaRepository>())
    }

    singleOf(::CreatorListRecyclerView)
    singleOf(::PizzaAdapter)
}