package com.example.pizza_shift_2024.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pizza_shift_2024.R
import com.example.pizza_shift_2024.databinding.ListItemBinding
import com.example.pizza_shift_2024.data.Pizza


class PizzaAdapter(val listener: Listener) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var itemList = ArrayList<Pizza>()
    private var costList = ArrayList<String>()

    class PizzaHolder(private val context: Context, item: View): RecyclerView.ViewHolder(item) {
        val binding = ListItemBinding.bind(item)
        fun bind(pizza: Pizza, cost: String, listener: Listener) {
            Glide.with(context).load("https://shift-backend.onrender.com" + pizza.img)
                .into(binding.picture)
            binding.name.text = pizza.name
            binding.description.text = pizza.description
            binding.price.text = cost

            itemView.setOnClickListener {
                listener.onClick(pizza)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return PizzaHolder(parent.context, view)

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = itemList[position]
        (holder as PizzaHolder).bind(item, costList[position], listener)

    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    fun initPizza(list: ArrayList<Pizza>?, cost: ArrayList<String>) {
        list?.let {
            itemList.clear()
            itemList.addAll(it)

            costList.clear()
            costList.addAll(cost)

            notifyDataSetChanged()
        }
    }

    interface Listener {
        fun onClick(pizza: Pizza)
    }
}