package com.example.schoolmealserver.trash.service

import com.example.schoolmealserver.trash.client.MealClient
import com.example.schoolmealserver.domain.school.payload.response.MealResponse
import org.springframework.stereotype.Service
import java.text.SimpleDateFormat
import java.util.*

@Service
class MealService(
        private val mealClient: MealClient
) {
    fun getMeal(): MealResponse {
        return mealClient.execute(
                key = "dfed562db5ef4e88b1e71079c0039615",
                type = "json",
                index = "1",
                size = "100",
                cityCode = "T10",
                schoolCode = "9290083",
                dateCode = SimpleDateFormat("yyyyMM").format(Date())
        )
    }
}