package com.example.pizza_shift_2024.feature.catalog.usecase

import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pizza_shift_2024.R
import com.example.pizza_shift_2024.feature.catalog.adapter.PizzaAdapter
import com.example.pizza_shift_2024.feature.catalog.domain.models.Pizza
import com.example.pizza_shift_2024.feature.catalog.domain.models.PizzaInformation
import com.example.pizza_shift_2024.feature.details.presentaion.PizzaDetailsFragment

class CreatorListRecyclerView(
    private val activity: FragmentActivity,
    private val pizza: PizzaInformation
    ): PizzaAdapter.Listener {
    private val listPictures = ArrayList<String>()
    private val listName = ArrayList<String>()
    private val listDescription = ArrayList<String>()
    private val listPrice = ArrayList<String>()
    private val adapter = PizzaAdapter(this)


    fun createPizzaCatalog(allPizza: RecyclerView) {
        fillListPizza()
        calculatePrice()
        initCatalogList(allPizza)
    }

    private fun fillListPizza() {
        if (pizza.success) {
            listPictures.clear()
            listName.clear()
            listDescription.clear()
            listPrice.clear()

            for (onePizza in pizza.catalog) {
                listPictures.add(onePizza.img)
                listName.add(onePizza.name)
                listDescription.add(onePizza.description)
            }

        } else {
            Toast.makeText(activity, "Что-то пошло не так",
                Toast.LENGTH_SHORT).show()
        }
    }

    private fun calculatePrice() {
        for (onePizza in pizza.catalog) {
            val price = onePizza.sizes[0].price + onePizza.doughs[0].price
            listPrice.add("от ${price} ₽")
        }
    }

    private fun initCatalogList(allPizza: RecyclerView) {

        allPizza.layoutManager = LinearLayoutManager(activity)
        allPizza.adapter = adapter
        adapter.initPizza(pizza.catalog, listPrice)
    }

    override fun onClick(pizza: Pizza) {
        val fragment = activity.supportFragmentManager.beginTransaction().replace(
            R.id.framePizza,
            PizzaDetailsFragment.newInstance(pizza)
        )
        fragment.addToBackStack(null).commit()
    }
}