package com.example.pizza_shift_2024

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.pizza_shift_2024.data.Pizza
import com.example.pizza_shift_2024.databinding.ActivityMainBinding
import com.example.pizza_shift_2024.databinding.FragmentPizzaDetailsBinding
import kotlinx.coroutines.flow.combine

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

        if (pizza != null) {
            with(binding) {
                Glide.with(context!!).
                    load("https://shift-backend.onrender.com" + pizza!!.img).into(picture)
                name.text = pizza!!.name
                description.text = pizza!!.description
            }
        }
        Toast.makeText(context, pizza?.name, Toast.LENGTH_SHORT).show()
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


}