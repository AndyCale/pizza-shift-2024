package com.example.pizza_shift_2024.feature.details.di

import com.example.pizza_shift_2024.adapters.AddAdapter
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val detailsPizzaModule = module {
    singleOf(::AddAdapter)
}