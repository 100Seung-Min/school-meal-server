package com.example.schoolmealserver.domain.meal.dto

data class MealDto(
        val mealServiceDietInfo: List<MealServiceDietInfo?>?
) {
    data class MealServiceDietInfo(
            val row: List<DietRow>?
    ) {
        data class DietRow(
                val DDISH_NM: String,
                val MLSV_YMD: String,
                val MMEAL_SC_NM: String,
        )
    }
}