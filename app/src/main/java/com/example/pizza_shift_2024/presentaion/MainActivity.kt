package com.example.pizza_shift_2024.presentaion

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.pizza_shift_2024.R
import com.example.pizza_shift_2024.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private val binding: ActivityMainBinding
        get() = _binding ?: throw IllegalStateException("Binding in Main Activity must not be null")

    private var timeLastPressed = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        createCatalogFragment()

        binding.reset.setOnClickListener {
            if (timeLastPressed + 10000 < System.currentTimeMillis()) {
                createCatalogFragment()
                timeLastPressed = System.currentTimeMillis()
            }
        }

    }

    private fun createCatalogFragment() {
        supportFragmentManager.beginTransaction().replace(
            R.id.framePizza,
            CatalogPizzaFragment.newInstance()
        ).commit()
    }
}
