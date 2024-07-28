package com.example.pizza_shift_2024.presentaion.usecase

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.pizza_shift_2024.data.PizzaRepository

class PizzaViewModelFactory(private val repository: PizzaRepository): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        require(modelClass == PizzaViewModel::class.java) { "Unknown ViewModel: $modelClass" }
        return PizzaViewModel(repository) as T
    }

}