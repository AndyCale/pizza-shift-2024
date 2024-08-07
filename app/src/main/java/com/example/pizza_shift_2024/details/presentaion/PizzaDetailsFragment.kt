package com.example.pizza_shift_2024.details.presentaion

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.example.pizza_shift_2024.R
import com.example.pizza_shift_2024.adapters.AddAdapter
import com.example.pizza_shift_2024.catalog.domain.models.Add
import com.example.pizza_shift_2024.catalog.domain.models.Pizza
import com.example.pizza_shift_2024.databinding.FragmentPizzaDetailsBinding

class PizzaDetailsFragment : Fragment(), AddAdapter.Listener {

    private var _binding: FragmentPizzaDetailsBinding? = null
    private val binding: FragmentPizzaDetailsBinding
        get() = _binding ?: throw IllegalStateException("Binding in PizzaDetails Fragment must not be null")

    private var pizza: Pizza? = null
    private val adapter = AddAdapter(this)

    var priceSize = 0
    var priceDough = 0
    var priceAdd = 0

    companion object {
        @JvmStatic
        fun newInstance(pizza: Pizza) = PizzaDetailsFragment().apply {
            arguments = Bundle().apply {
                putSerializable("pizza", pizza)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            pizza = it.getSerializable("pizza") as? Pizza ?:
            error("Pizza is required on PizzaDetailesScreen. But it was null")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPizzaDetailsBinding.inflate(inflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initFragment()

        binding.back.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }

        binding.size.setOnCheckedChangeListener { radioGroup, id ->
            when(id) {
                R.id.small -> {
                    pizza?.let{pizza -> priceSize = pizza.sizes[0].price}
                }
                R.id.medium -> {
                    pizza?.let{pizza -> priceSize = pizza.sizes[1].price}
                }
                R.id.big -> {
                    pizza?.let{pizza -> priceSize = pizza.sizes[2].price}
                }
            }
            binding.price.text = "${priceSize + priceDough + priceAdd} ₽"
        }

        binding.dough.setOnCheckedChangeListener { radioGroup, id ->
            when(id) {
                R.id.thin -> {
                    pizza?.let{pizza -> priceDough = pizza.doughs[0].price}
                }
                R.id.thick -> {
                    pizza?.let{pizza -> priceDough = pizza.doughs[1].price}
                }
            }
            binding.price.text = "${priceSize + priceDough + priceAdd} ₽"
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initFragment() {
        if (pizza == null) {
            Toast.makeText(requireContext(),"Что-то пошло не так", Toast.LENGTH_SHORT).show()
            requireActivity().supportFragmentManager.popBackStack()
        }

        with(binding) {
            pizza?.let { pizza ->

                Glide.with(requireContext()).load("https://shift-backend.onrender.com${pizza.img}")
                    .into(picture)
                name.text = pizza.name
                description.text = pizza.description

                small.isChecked = true
                thin.isChecked = true
                priceSize = pizza.sizes[0].price
                priceDough = pizza.doughs[0].price
                price.text = "${priceSize + priceDough} ₽"

                calories.text = pizza.calories
                protein.text = pizza.protein
                totalFat.text = pizza.totalFat
                carbohydrates.text = pizza.carbohydrates

            }
            initAddList()
        }
    }

    private fun initAddList() {
        pizza?.let { pizza ->
            binding.add.layoutManager = GridLayoutManager(requireContext(), 3)
            binding.add.adapter = adapter
            adapter.initAdd(pizza.toppings)
        }
    }

    override fun onClick(add: Add) {
        if (add.use) {
            Toast.makeText(requireContext(), "Вы добавили ${add.name.lowercase()}", Toast.LENGTH_SHORT).show()
            priceAdd += add.cost

        } else {
            Toast.makeText(requireContext(), "Вы убрали ${add.name.lowercase()}", Toast.LENGTH_SHORT).show()
            priceAdd -= add.cost
        }
        binding.price.text = "${priceSize + priceDough + priceAdd} ₽"
    }

}