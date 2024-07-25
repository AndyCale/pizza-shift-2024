package com.example.pizza_shift_2024.presentaion.usecase

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pizza_shift_2024.data.PizzaAPI
import com.example.pizza_shift_2024.data.PizzaRepository
import com.example.pizza_shift_2024.domain.models.PizzaInformation
import kotlinx.coroutines.*

class PizzaViewModel : ViewModel() {
    private val _pizza = MutableLiveData<PizzaInformation>()
    val pizza: LiveData<PizzaInformation> get() = _pizza
    val pizzaAPI = PizzaRepository().retrofit.create(PizzaAPI::class.java)

    init {
        Log.d("PizzaApi", "created")
        initializatioData()
    }

    fun reset() {
        initializatioData()
    }

    private fun initializatioData() {
        viewModelScope.launch {
            try {
                _pizza.value = pizzaAPI.getPizza()
            } catch (ce : CancellationException) {
                throw ce
            } catch (ex : Exception) {
                Log.d("PizzaApi", "Что-то пошло не так! $ex")
            }
        }
    }
}