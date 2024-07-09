package com.example.pizza_shift_2024

import android.app.LauncherActivity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pizza_shift_2024.data.Add
import com.example.pizza_shift_2024.data.Pizza
import com.example.pizza_shift_2024.databinding.AddItemBinding
import com.example.pizza_shift_2024.databinding.ListItemBinding

class MultipleAdapter(val listener: Listener) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var itemList = ArrayList<LauncherActivity.ListItem>()
    private var costList = ArrayList<String>()

    class AddHolder(private val context: Context, item: View): RecyclerView.ViewHolder(item) {
        val binding = AddItemBinding.bind(item)
        fun bind(add: Add, listener: Listener) {
            Glide.with(context).load("https://shift-backend.onrender.com" + add.img)
                .into(binding.picture)
            binding.nameA.text = add.name.lowercase()
            binding.priceA.text = add.cost.toString() + " ₽"

            itemView.setOnClickListener {
                listener.onClick(add)
            }
        }
    }

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
        return when (viewType) {
            0 -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.add_item, parent, false)
                AddHolder(parent.context, view)
            }
            1 -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
                PizzaHolder(parent.context, view)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = itemList[position]) {
            is Add -> (holder as AddHolder).bind(item, listener)
            is Pizza -> (holder as PizzaHolder).bind(item, costList[position], listener)
        }
    }

    override fun getItemViewType(position: Int): Int {
        when (val item = itemList[position]) {
            is Add -> return 0
            is Pizza -> return 1
            else -> throw IllegalStateException("Binding in CatalogPizza Fragment must not be null")
        }
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    fun initItems(list: ArrayList<out LauncherActivity.ListItem>?) {
        list?.let {
            itemList.clear()
            itemList.addAll(it)
            notifyDataSetChanged()
        }
    }

    fun initItems(list: ArrayList<out LauncherActivity.ListItem>?, cost: ArrayList<String>) {
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
        fun onClick(add: Add)
    }
}