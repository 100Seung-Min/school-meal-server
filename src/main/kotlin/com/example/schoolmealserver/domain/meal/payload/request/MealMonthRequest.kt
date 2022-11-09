package com.example.schoolmealserver.domain.meal.payload.request

data class MealMonthRequest(
        val cityCode: String,
        val schoolCode: String,
        val month: String
)
