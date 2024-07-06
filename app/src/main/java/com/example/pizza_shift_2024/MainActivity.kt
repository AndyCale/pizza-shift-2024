package com.example.pizza_shift_2024

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.pizza_shift_2024.data.PizzaAPI
import com.example.pizza_shift_2024.data.PizzaInformation
import com.example.pizza_shift_2024.databinding.ActivityMainBinding
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


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
    private lateinit var pizza: PizzaInformation


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fillListOfPizza()

        binding.allPizza.setOnItemClickListener { parent, view, position, id ->
            supportFragmentManager.beginTransaction().replace(R.id.frameInfoPizza,
                PizzaDetailsFragment.newInstance(pizza.catalog[position])).commit()

            //Toast.makeText(this, position.toString(), Toast.LENGTH_SHORT).show()
        }

    }

    private fun fillListOfPizza() {
        lifecycleScope.launch() {
            pizza = pizzaAPI.getPizza()

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

    override fun onBackPressed() {
        val count = supportFragmentManager.backStackEntryCount
        if (count == 0) {
            super.onBackPressed()
            //additional code
        } else {
            supportFragmentManager.popBackStack()
        }
    }
}