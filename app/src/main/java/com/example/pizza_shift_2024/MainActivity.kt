package com.example.pizza_shift_2024

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.pizza_shift_2024.data.PizzaAPI
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
    private val listPrice = ArrayList<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val dexOutputDir: File = codeCacheDir
        dexOutputDir.setReadOnly()

        fillListOfPizza()

        binding.allPizza.setOnItemClickListener { parent, view, position, id ->
            Toast.makeText(this, position.toString(), Toast.LENGTH_SHORT).show()
        }

    }

    private fun fillListOfPizza() {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://shift-backend.onrender.com/")
            .addConverterFactory(GsonConverterFactory.create()).build()
        val pizzaAPI = retrofit.create(PizzaAPI::class.java)


        CoroutineScope(Dispatchers.IO).launch() {
            val pizza = pizzaAPI.getPizza()

            if (pizza.success == true) {
                runOnUiThread {
                    for (onePizza in pizza.catalog) {
                        listPictures.add(onePizza.img)
                        listName.add(onePizza.name)
                        listDescription.add(onePizza.description)
                        listPrice.add(0) // цена из цены за размер + тесто

                    }

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

    private fun calculatePrice() {

    }
}