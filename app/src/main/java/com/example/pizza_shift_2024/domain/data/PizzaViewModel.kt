package com.example.pizza_shift_2024.domain.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*

class PizzaViewModel : ViewModel() {
    private val _pizza = MutableLiveData<PizzaInformation>()
    val pizza: LiveData<PizzaInformation> get() = _pizza

    init {
        val pizzaAPI = PizzaRepository().retrofit.create(PizzaAPI::class.java)

        viewModelScope.launch {
            _pizza.value = pizzaAPI.getPizza()
        }
    }
}