package com.example.pizza_shift_2024.catalog.domain.models

import android.app.LauncherActivity
import java.io.Serializable


data class PizzaInformation(
    val success: Boolean,
    val reason: String,
    val catalog: ArrayList<Pizza>
) : Serializable

data class Pizza(
    val id: String,
    val name: String,
    val ingredients: List<Ingredients>, // ---------------
    val toppings: ArrayList<Add>,
    val allergens: List<String>, // ----------------
    val description: String,
    val sizes: List<Size>,
    val doughs: List<Size>,
    val calories: String,
    val protein: String,
    val totalFat: String,
    val carbohydrates: String,
    val img: String

    /*
      "sodium": "string",
      "isVegetarian": true,
      "isGlutenFree": true,
      "isNew": true,
      "isHit": true,

     */
) : Serializable, LauncherActivity.ListItem()

data class Ingredients(
    val name: String,
    val coast: Int,
    val img: String
)

data class Size(
    val name: String,
    val price: Int
)

data class Add(
    val name: String,
    val img: String,
    val cost: Int,
    var use: Boolean = false
) : LauncherActivity.ListItem()