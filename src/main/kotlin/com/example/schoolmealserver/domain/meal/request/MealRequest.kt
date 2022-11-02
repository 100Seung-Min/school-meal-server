package com.example.schoolmealserver.domain.meal.request

data class MealRequest(
        val cityCode: String,
        val schoolCode: String,
        val day: String
)
