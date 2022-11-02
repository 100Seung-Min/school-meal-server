package com.example.schoolmealserver.domain.meal.request

data class MealMonthRequest(
        val cityCode: String,
        val schoolCode: String,
        val month: String
)
