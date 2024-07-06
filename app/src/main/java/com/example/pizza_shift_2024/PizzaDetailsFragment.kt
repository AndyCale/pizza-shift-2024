package com.example.pizza_shift_2024

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.pizza_shift_2024.data.Pizza
import com.example.pizza_shift_2024.databinding.FragmentPizzaDetailsBinding


//private const val ARG_PARAM1 = "param1"
//private const val ARG_PARAM2 = "param2"


/*
<androidx.fragment.app.FragmentContainerView
        android:id="@+id/frameInfoPizza"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:name="com.example.pizza_shift_2024.PizzaDetailsFragment"
        tools:layout="@layout/fragment_pizza_details"/>
 */

class PizzaDetailsFragment : Fragment() {

    private var _binding: FragmentPizzaDetailsBinding? = null
    private val binding: FragmentPizzaDetailsBinding
        get() = _binding ?: throw IllegalStateException("Binding in Main Activity must not be null")

    private var pizza: Pizza? = null

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
        //return inflater.inflate(R.layout.fragment_pizza_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (pizza == null) {
            // завершить фрагмент с ошибкой
        }
        with(binding) {
            Glide.with(context!!).
                load("https://shift-backend.onrender.com" + pizza!!.img).into(picture)
            name.text = pizza!!.name
            description.text = pizza!!.description
        }

        binding.small.isChecked = true
        binding.thin.isChecked = true
        priceSize = pizza!!.sizes[0].price
        priceDough = pizza!!.doughs[0].price
        binding.price.text = "${priceSize + priceDough} ₽"

        binding.calories.text = pizza!!.calories
        binding.protein.text = pizza!!.protein
        binding.totalFat.text = pizza!!.totalFat
        binding.carbohydrates.text = pizza!!.carbohydrates

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


        //Toast.makeText(context, pizza?.name, Toast.LENGTH_SHORT).show()
        // здесь через binding можем что-то делать с элементами, например нажать кнопку в радио кнопке
    }

    /*
    companion object {
        @JvmStatic
        fun newInstance() = PizzaDetailsFragment()
    }

     */


    companion object {
        @JvmStatic
        fun newInstance(pizza: Pizza) = PizzaDetailsFragment().apply {
                arguments = Bundle().apply {
                    putSerializable("pizza", pizza)
                }
            }
    }


    private fun calculatePrice(pizza: Pizza) {

    }

}