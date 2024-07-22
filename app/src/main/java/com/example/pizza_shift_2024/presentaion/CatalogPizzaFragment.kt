package com.example.pizza_shift_2024.presentaion

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pizza_shift_2024.R
import com.example.pizza_shift_2024.adapters.PizzaAdapter
import com.example.pizza_shift_2024.databinding.FragmentCatalogPizzaBinding
import com.example.pizza_shift_2024.domain.data.Pizza
import com.example.pizza_shift_2024.domain.data.PizzaInformation
import com.example.pizza_shift_2024.domain.data.PizzaViewModel

class CatalogPizzaFragment : Fragment(), PizzaAdapter.Listener {

    private var _binding: FragmentCatalogPizzaBinding? = null
    private val binding: FragmentCatalogPizzaBinding
        get() = _binding ?: throw IllegalStateException("Binding in CatalogPizza Fragment must not be null")

    private val adapter = PizzaAdapter(this)

    private val listPictures = ArrayList<String>()
    private val listName = ArrayList<String>()
    private val listDescription = ArrayList<String>()
    private val listPrice = ArrayList<String>()

    private lateinit var viewModel: PizzaViewModel

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

        viewModel = ViewModelProvider(this).get(PizzaViewModel::class.java)

        viewModel.pizza.observe(this, Observer { pizza ->
            if (pizza.success) {
                createPizzaCatalog(pizza)
            }
            else {
                Toast.makeText(requireContext(), R.string.error, Toast.LENGTH_SHORT).show()
                requireActivity().supportFragmentManager.popBackStack()
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun createPizzaCatalog(pizza: PizzaInformation) {
        fillListPizza(pizza)
        calculatePrice(pizza)
        initCatalogList(pizza)
    }

    private fun fillListPizza(pizza : PizzaInformation) {
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
        Toast.makeText(requireActivity(), "Что-то пошло не так",
            Toast.LENGTH_SHORT).show()
        }
    }

    private fun calculatePrice(pizza: PizzaInformation) {
        for (onePizza in pizza.catalog) {
            val price = onePizza.sizes[0].price + onePizza.doughs[0].price

            listPrice.add("от ${price} ₽")
        }
    }

    private fun initCatalogList(pizza: PizzaInformation) {
        binding.allPizza.layoutManager = LinearLayoutManager(requireContext())
        binding.allPizza.adapter = adapter
        adapter.initPizza(pizza.catalog, listPrice)
    }

    override fun onClick(pizza: Pizza) {
        val fragment = requireActivity().supportFragmentManager.beginTransaction().replace(
            R.id.framePizza,
            PizzaDetailsFragment.newInstance(pizza)
        )
        fragment.addToBackStack(null)
        fragment.commit()
    }

}