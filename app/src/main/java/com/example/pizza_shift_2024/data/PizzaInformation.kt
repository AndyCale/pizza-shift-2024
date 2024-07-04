package com.example.pizza_shift_2024.data

data class PizzaInformation(
    val success: Boolean,
    val reason: String,
    val catalog: List<Pizza>
    )

data class Pizza(
    val id: String,
    val name: String,
    val ingredients: List<Ingredients>,
    val toppings: List<Ingredients>,
    val description: String,
    val sizes: List<Size>,
    val doughs: List<Size>,
    val calories: Int,
    val protein: String,
    val totalFat: String,
    val img: String

    /*
    val carbohydrates: String,
      "sodium": "string",
      "allergens": [
        "string"
      ],
      "isVegetarian": true,
      "isGlutenFree": true,
      "isNew": true,
      "isHit": true,

     */
)

data class Ingredients(
    val name: String,
    val coast: Int,
    val img: String
)

data class Size(
    val name: String,
    val price: Int
)