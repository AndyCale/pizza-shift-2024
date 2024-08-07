package com.example.pizza_shift_2024.app.presentaion

import android.app.Application
import com.example.pizza_shift_2024.catalog.domain.data.pizzaRepository
import com.example.pizza_shift_2024.app.di.catalogPizzaModule
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class ApplicationDI : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger(Level.ERROR)
            modules(
                pizzaRepository,
                catalogPizzaModule,

            )
        }
    }
}