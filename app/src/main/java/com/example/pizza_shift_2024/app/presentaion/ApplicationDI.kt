package com.example.pizza_shift_2024.app.presentaion

import android.app.Application
import com.example.pizza_shift_2024.feature.catalog.domain.data.pizzaRepository
import com.example.pizza_shift_2024.app.di.catalogPizzaModule
import com.example.pizza_shift_2024.feature.details.di.detailsPizzaModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class ApplicationDI : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger(Level.ERROR)
            //androidContext(this@ApplicationDI)
            modules(
                pizzaRepository,
                catalogPizzaModule,
                detailsPizzaModule,
            )
        }
    }
}