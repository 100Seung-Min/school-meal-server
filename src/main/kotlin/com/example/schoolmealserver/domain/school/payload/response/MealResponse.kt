package com.example.schoolmealserver.domain.school.payload.response

data class MealResponse(
        val dishName: String,
        val mealDay: String,
        val mealTime: String
)