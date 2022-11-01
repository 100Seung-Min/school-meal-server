package com.example.schoolmealserver.domain.meal.dto

import com.google.gson.annotations.SerializedName

data class MealDto(
        @SerializedName("mealServiceDietInfo")
        val mealServiceDietInfo: List<MealServiceDietInfo?>?
) {
    data class MealServiceDietInfo(
            @SerializedName("row")
            val row: List<DietRow>?
    ) {
        data class DietRow(
                @SerializedName("DDISH_NM")
                val dishName: String,
                @SerializedName("MLSV_YMD")
                val mealDay: String,
                @SerializedName("MMEAL_SC_NM")
                val mealTime: String,
        )
    }
}