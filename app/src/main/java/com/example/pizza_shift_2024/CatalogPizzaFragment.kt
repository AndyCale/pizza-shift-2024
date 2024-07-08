package com.example.pizza_shift_2024

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.example.pizza_shift_2024.data.Pizza
import com.example.pizza_shift_2024.data.PizzaAPI
import com.example.pizza_shift_2024.data.PizzaInformation
import com.example.pizza_shift_2024.databinding.FragmentCatalogPizzaBinding
import com.example.pizza_shift_2024.databinding.FragmentPizzaDetailsBinding
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CatalogPizzaFragment : Fragment() {

    private var _binding: FragmentCatalogPizzaBinding? = null
    private val binding: FragmentCatalogPizzaBinding
        get() = _binding ?: throw IllegalStateException("Binding in CatalogPizza Fragment must not be null")

    private val adapter = AddAdapter()

    private val listPictures = ArrayList<String>()
    private val listName = ArrayList<String>()
    private val listDescription = ArrayList<String>()
    private val listPrice = ArrayList<String>()

    val retrofit = Retrofit.Builder()
        .baseUrl("https://shift-backend.onrender.com/")
        .addConverterFactory(GsonConverterFactory.create()).build()
    val pizzaAPI = retrofit.create(PizzaAPI::class.java)
    private lateinit var pizza: PizzaInformation

    companion object {
        @JvmStatic
        fun newInstance() = CatalogPizzaFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        _binding = FragmentCatalogPizzaBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fillListOfPizza()

        binding.allPizza.setOnItemClickListener { parent, view, position, id ->
            requireActivity().supportFragmentManager.beginTransaction().replace(R.id.framePizza,
                PizzaDetailsFragment.newInstance(pizza.catalog[position])).commit()

            //Toast.makeText(this, position.toString(), Toast.LENGTH_SHORT).show()
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun fillListOfPizza() {
        lifecycleScope.launch() {
            pizza = pizzaAPI.getPizza()

            if (pizza.success == true) {

                    for (onePizza in pizza.catalog) {
                        listPictures.add(onePizza.img)
                        listName.add(onePizza.name)
                        listDescription.add(onePizza.description)
                    }

                    calculatePrice(pizza)

                    val myAdapter = CustomBaseAdapter(requireActivity(), listPictures,
                        listName, listDescription, listPrice)
                    binding.allPizza.adapter = myAdapter


                    //binding.pizza.text = pizza.catalog[0].name.toString()

            }

            else {
                Toast.makeText(requireActivity(), "Что-то пошло не так",
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

