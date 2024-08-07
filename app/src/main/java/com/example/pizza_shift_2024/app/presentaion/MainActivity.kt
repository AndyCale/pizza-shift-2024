package com.example.pizza_shift_2024.app.presentaion

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.pizza_shift_2024.R
import com.example.pizza_shift_2024.catalog.presentaion.CatalogPizzaFragment
import com.example.pizza_shift_2024.catalog.domain.data.PizzaRepository
import com.example.pizza_shift_2024.databinding.ActivityMainBinding
import com.example.pizza_shift_2024.app.presentaion.usecase.PizzaViewModel
import com.example.pizza_shift_2024.app.presentaion.usecase.PizzaViewModelFactory
import com.example.pizza_shift_2024.catalog.domain.models.PizzaInformation


class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private val binding: ActivityMainBinding
        get() = _binding ?: throw IllegalStateException("Binding in Main Activity must not be null")

    private var timeLastPressed = 0L

    private val repository = PizzaRepository()

    private val viewModel: PizzaViewModel by lazy {
        ViewModelProvider(this, PizzaViewModelFactory(repository)).get(PizzaViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("PizzaApi", "1")
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Log.d("PizzaApi", "2")
        viewModel.pizza.observe(this, Observer { pizza ->
            if (pizza != null && pizza.success) {
                createCatalogFragment(pizza)
            }
            else {
                Toast.makeText(this, R.string.error, Toast.LENGTH_SHORT).show()
                supportFragmentManager.popBackStack()
            }
        })

        binding.reset.setOnClickListener {
            if (timeLastPressed + 10000 < System.currentTimeMillis()) {
                viewModel.reset()
                timeLastPressed = System.currentTimeMillis()
            }
        }
    }

    private fun createCatalogFragment(pizza : PizzaInformation) {
        supportFragmentManager.beginTransaction().replace(
            R.id.framePizza,
            CatalogPizzaFragment.newInstance(pizza)
        ).commit()
    }
}
