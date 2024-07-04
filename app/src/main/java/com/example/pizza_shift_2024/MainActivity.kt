package com.example.pizza_shift_2024

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.pizza_shift_2024.data.PizzaAPI
import com.example.pizza_shift_2024.data.PizzaInformation
import com.example.pizza_shift_2024.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.Dispatcher
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.io.File
import kotlin.coroutines.*


class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private val binding: ActivityMainBinding
        get() = _binding ?: throw IllegalStateException("Binding in Main Activity must not be null")

    private val listPictures = ArrayList<String>()
    private val listName = ArrayList<String>()
    private val listDescription = ArrayList<String>()
    private val listPrice = ArrayList<String>()

    val retrofit = Retrofit.Builder()
        .baseUrl("https://shift-backend.onrender.com/")
        .addConverterFactory(GsonConverterFactory.create()).build()
    val pizzaAPI = retrofit.create(PizzaAPI::class.java)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fillListOfPizza()

        binding.allPizza.setOnItemClickListener { parent, view, position, id ->
            Toast.makeText(this, position.toString(), Toast.LENGTH_SHORT).show()
        }

    }

    private fun fillListOfPizza() {
        lifecycleScope.launch() {
            val pizza = pizzaAPI.getPizza()

            if (pizza.success == true) {
                runOnUiThread {
                    for (onePizza in pizza.catalog) {
                        listPictures.add(onePizza.img)
                        listName.add(onePizza.name)
                        listDescription.add(onePizza.description)
                    }

                    calculatePrice(pizza)

                    val myAdapter = CustomBaseAdapter(this@MainActivity, listPictures,
                        listName, listDescription, listPrice)
                    binding.allPizza.adapter = myAdapter


                    //binding.pizza.text = pizza.catalog[0].name.toString()
                }
            }

            else {
                Toast.makeText(this@MainActivity, "Что-то пошло не так",
                    Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun calculatePrice(pizza: PizzaInformation) {
        for (onePizza in pizza.catalog) {
            val price = onePizza.sizes[0].price + onePizza.doughs[0].price

            listPrice.add("от ${price} ₽") // цена из цены за размер + тесто


        }
    }
}