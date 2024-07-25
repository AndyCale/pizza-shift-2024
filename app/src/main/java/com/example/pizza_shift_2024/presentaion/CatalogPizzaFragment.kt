package com.example.pizza_shift_2024.presentaion

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.pizza_shift_2024.R
import com.example.pizza_shift_2024.databinding.FragmentCatalogPizzaBinding
import com.example.pizza_shift_2024.domain.models.Pizza
import com.example.pizza_shift_2024.domain.models.PizzaInformation
import com.example.pizza_shift_2024.presentaion.usecase.PizzaViewModel
import com.example.pizza_shift_2024.presentaion.usecase.CreatorListRecyclerView

class CatalogPizzaFragment : Fragment() {

    private var _binding: FragmentCatalogPizzaBinding? = null
    private val binding: FragmentCatalogPizzaBinding
        get() = _binding ?: throw IllegalStateException("Binding in CatalogPizza Fragment must not be null")

    private lateinit var pizza: PizzaInformation

    companion object {
        @JvmStatic
        fun newInstance(pizza: PizzaInformation) = CatalogPizzaFragment().apply {
            arguments = Bundle().apply {
                putSerializable("pizza", pizza)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            pizza = it.getSerializable("pizza") as? PizzaInformation ?:
                    error("Pizza is required on PizzaDetailesScreen. But it was null")
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        _binding = FragmentCatalogPizzaBinding.inflate(inflater)
        CreatorListRecyclerView(requireActivity(), pizza).createPizzaCatalog(binding.allPizza)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d("PizzaApi", "deleted")
        _binding = null
    }
}