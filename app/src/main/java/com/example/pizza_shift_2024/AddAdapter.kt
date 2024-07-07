package com.example.pizza_shift_2024

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pizza_shift_2024.data.Add
import com.example.pizza_shift_2024.databinding.AddItemBinding

class AddAdapter: RecyclerView.Adapter<AddAdapter.AddHolder>() {
    private var addList = ArrayList<Add>()
    class AddHolder(private val context: Context, item: View): RecyclerView.ViewHolder(item) {
        val binding = AddItemBinding.bind(item)
        fun bind(add: Add) {
            Glide.with(context).load("https://shift-backend.onrender.com" + add.img)
                .into(binding.picture)
            binding.nameA.text = add.name.lowercase()
            binding.priceA.text = add.cost.toString().lowercase() + " ₽"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.add_item, parent, false)
        return AddHolder(parent.context, view)
    }

    override fun onBindViewHolder(holder: AddHolder, position: Int) {
        holder.bind(addList[position])
    }

    override fun getItemCount(): Int {
        return addList.size
    }

    fun initAdd(list: ArrayList<Add>?) {
        if (list != null) {
            addList = list
        }
        notifyDataSetChanged()
    }
}
