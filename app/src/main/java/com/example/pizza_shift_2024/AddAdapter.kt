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

class AddAdapter(val listener: Listener) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var itemList = ArrayList<Add>()

    class AddHolder(private val context: Context, item: View): RecyclerView.ViewHolder(item) {
        val binding = AddItemBinding.bind(item)
        fun bind(add: Add, listener: Listener) {
            Glide.with(context).load("https://shift-backend.onrender.com" + add.img)
                .into(binding.picture)
            binding.nameA.text = add.name.lowercase()
            binding.priceA.text = add.cost.toString() + " ₽"

            itemView.setOnClickListener {
                if (add.use) {
                    binding.card.setBackgroundResource(R.color.white)
                    add.use = false
                } else {
                    binding.card.setBackgroundResource(R.color.light_grey)
                    add.use = true
                }

                listener.onClick(add)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.add_item, parent, false)
        return AddHolder(parent.context, view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = itemList[position]
        (holder as AddHolder).bind(item, listener)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    fun initAdd(list: ArrayList<Add>) {
        itemList.clear()
        itemList.addAll(list)
        notifyDataSetChanged()
    }

    interface Listener {
        fun onClick(add: Add)
    }
}
