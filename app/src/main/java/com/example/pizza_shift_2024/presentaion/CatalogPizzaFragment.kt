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
import com.example.pizza_shift_2024.presentaion.usecase.PizzaViewModel
import com.example.pizza_shift_2024.presentaion.usecase.CreatorListRecyclerView

class CatalogPizzaFragment : Fragment() {

    private var _binding: FragmentCatalogPizzaBinding? = null
    private val binding: FragmentCatalogPizzaBinding
        get() = _binding ?: throw IllegalStateException("Binding in CatalogPizza Fragment must not be null")

    private val viewModel: PizzaViewModel by lazy {
        ViewModelProvider(this).get(PizzaViewModel::class.java)
    }

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

        viewModel.pizza.observe(this, Observer { pizza ->
            if (pizza != null && pizza.success) {
                CreatorListRecyclerView(requireActivity(), pizza).createPizzaCatalog(binding.allPizza)
            }
            else {
                Toast.makeText(requireContext(), R.string.error, Toast.LENGTH_SHORT).show()
                requireActivity().supportFragmentManager.popBackStack()
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d("PizzaApi", "deleted")
        _binding = null
    }
}