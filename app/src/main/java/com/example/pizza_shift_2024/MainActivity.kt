package com.example.pizza_shift_2024

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
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

    private var timeLastPressed = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager.beginTransaction().replace(R.id.framePizza,
            CatalogPizzaFragment.newInstance()).commit()

        binding.reset.setOnClickListener {
            if (timeLastPressed + 10000 < System.currentTimeMillis()) {
                supportFragmentManager.beginTransaction().replace(
                    R.id.framePizza,
                    CatalogPizzaFragment.newInstance()
                ).commit()
                timeLastPressed = System.currentTimeMillis()
            }
        }

    }

    override fun onBackPressed() {
        val count = supportFragmentManager.backStackEntryCount
        if (count == 0) {
            super.onBackPressed()
        } else {
            supportFragmentManager.popBackStack()
        }
    }
}
