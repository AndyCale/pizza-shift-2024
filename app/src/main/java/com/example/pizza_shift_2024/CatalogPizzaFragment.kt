package com.example.pizza_shift_2024

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pizza_shift_2024.data.Add
import com.example.pizza_shift_2024.data.Pizza
import com.example.pizza_shift_2024.data.PizzaAPI
import com.example.pizza_shift_2024.data.PizzaInformation
import com.example.pizza_shift_2024.databinding.FragmentCatalogPizzaBinding
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CatalogPizzaFragment : Fragment(), PizzaAdapter.Listener {

    private var _binding: FragmentCatalogPizzaBinding? = null
    private val binding: FragmentCatalogPizzaBinding
        get() = _binding ?: throw IllegalStateException("Binding in CatalogPizza Fragment must not be null")

    private val adapter = PizzaAdapter(this)

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
                initCatalogList()
            } else {
                Toast.makeText(requireActivity(), "Что-то пошло не так",
                    Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun calculatePrice(pizza: PizzaInformation) {
        for (onePizza in pizza.catalog) {
            val price = onePizza.sizes[0].price + onePizza.doughs[0].price

            listPrice.add("от ${price} ₽")
        }
    }

    private fun initCatalogList() {
        binding.allPizza.layoutManager = LinearLayoutManager(requireContext())
        binding.allPizza.adapter = adapter
        adapter.initPizza(pizza.catalog, listPrice)
    }

    override fun onClick(pizza: Pizza) {
        val fragment = requireActivity().supportFragmentManager.beginTransaction().replace(R.id.framePizza,
            PizzaDetailsFragment.newInstance(pizza))
        fragment.addToBackStack(null)
        fragment.commit()
    }

}