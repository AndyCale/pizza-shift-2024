package com.example.pizza_shift_2024

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide

class CustomBaseAdapter(private val context:Activity, private val listPicture : ArrayList<String>,
                        private val listName: ArrayList<String>,
                        private val listDescription: ArrayList<String>,
                        private val listPrice: ArrayList<String>) :
    ArrayAdapter<String>(context, R.layout.list_item, listName) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater : LayoutInflater = context.layoutInflater
        val rowView = inflater.inflate(R.layout.list_item, null, true)

        val name : TextView = rowView.findViewById(R.id.name) as TextView
        val image : ImageView = rowView.findViewById(R.id.picture) as ImageView
        val descr : TextView = rowView.findViewById(R.id.description) as TextView
        val price : TextView = rowView.findViewById(R.id.price) as TextView

        name.text = listName[position]
        Glide.with(context).load("https://shift-backend.onrender.com" + listPicture[position]).into(image)
        descr.text = listDescription[position]
        price.text = listPrice[position]

        return rowView
    }
}