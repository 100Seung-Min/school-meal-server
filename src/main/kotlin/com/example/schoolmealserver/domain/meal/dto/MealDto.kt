package com.example.schoolmealserver.domain.meal.dto

data class MealDto(
        val row: List<MealItem>
) {
    data class MealItem(
            val dishName: String,
            val mealDay: String,
            val mealTime: String,
    )
}