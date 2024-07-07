package com.example.pizza_shift_2024

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.example.pizza_shift_2024.data.Add
import com.example.pizza_shift_2024.data.Pizza
import com.example.pizza_shift_2024.databinding.FragmentPizzaDetailsBinding

class PizzaDetailsFragment : Fragment() {

    private var _binding: FragmentPizzaDetailsBinding? = null
    private val binding: FragmentPizzaDetailsBinding
        get() = _binding ?: throw IllegalStateException("Binding in Main Activity must not be null")

    private var pizza: Pizza? = null
    private val adapter = AddAdapter()

    var priceSize = 0
    var priceDough = 0
    var priceAdd = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            pizza = it.getSerializable("pizza") as Pizza?
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

        initialFragment()

        binding.back.setOnClickListener {
            val fm: FragmentManager? = fragmentManager
            val ft: FragmentTransaction = fm!!.beginTransaction()
            ft.remove(this)
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE)
            ft.commit()
        }

        binding.size.setOnCheckedChangeListener { radioGroup, id ->
            when(id) {
                R.id.small -> {
                    priceSize = pizza!!.sizes[0].price
                }
                R.id.medium -> {
                    priceSize = pizza!!.sizes[1].price
                }
                R.id.big -> {
                    priceSize = pizza!!.sizes[2].price
                }
            }
            binding.price.text = "${priceSize + priceDough} ₽"
        }

        binding.dough.setOnCheckedChangeListener { radioGroup, id ->
            when(id) {
                R.id.thin -> {
                    priceDough = pizza!!.doughs[0].price
                }
                R.id.thick -> {
                    priceDough = pizza!!.doughs[1].price
                }
            }
            binding.price.text = "${priceSize + priceDough} ₽"
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(pizza: Pizza) = PizzaDetailsFragment().apply {
                arguments = Bundle().apply {
                    putSerializable("pizza", pizza)
                }
            }
    }

    private fun initialFragment() {
        if (pizza == null) {
            Toast.makeText(context,"Что-то пошло не так", Toast.LENGTH_SHORT).show()
            val fm: FragmentManager? = fragmentManager
            val ft: FragmentTransaction = fm!!.beginTransaction()
            ft.remove(this)
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE)
            ft.commit()
        }

        with(binding) {
            Glide.with(context!!).load("https://shift-backend.onrender.com" + pizza!!.img)
                .into(picture)
            name.text = pizza!!.name
            description.text = pizza!!.description

            small.isChecked = true
            thin.isChecked = true
            priceSize = pizza!!.sizes[0].price
            priceDough = pizza!!.doughs[0].price
            price.text = "${priceSize + priceDough} ₽"

            calories.text = pizza!!.calories
            protein.text = pizza!!.protein
            totalFat.text = pizza!!.totalFat
            carbohydrates.text = pizza!!.carbohydrates

            initAddList()
        }
    }

    private fun initAddList() {
        binding.add.layoutManager = GridLayoutManager(context, 3)
        binding.add.adapter = adapter
        adapter.initAdd(pizza?.toppings)
    }
}