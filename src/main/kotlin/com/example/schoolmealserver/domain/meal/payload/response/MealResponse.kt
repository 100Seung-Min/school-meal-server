package com.example.schoolmealserver.domain.meal.payload.response

data class MealResponse(
        val row: List<MealItem>
) {
    data class MealItem(
            val dishName: String,
            val mealDay: String,
            val mealTime: String,
    )
}