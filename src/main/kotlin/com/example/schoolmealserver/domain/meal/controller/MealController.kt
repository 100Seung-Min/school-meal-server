package com.example.schoolmealserver.domain.meal.controller

import com.example.schoolmealserver.domain.meal.payload.response.MealResponse
import com.example.schoolmealserver.domain.meal.payload.request.MealMonthRequest
import com.example.schoolmealserver.domain.meal.payload.request.MealRequest
import com.example.schoolmealserver.global.openapi.URLList
import com.example.schoolmealserver.global.openapi.connectMeal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.net.URL

@RestController
@RequestMapping("/meal")
class MealController {
    @GetMapping
    fun meal(
            @RequestBody mealRequest: MealRequest
    ): MealResponse? {
        return connectMeal(URL("${URLList.meal}ATPT_OFCDC_SC_CODE=${mealRequest.cityCode}&SD_SCHUL_CODE=${mealRequest.schoolCode}&MLSV_YMD=${mealRequest.day}"))
    }

    @GetMapping("/month")
    fun mealMonth(
            @RequestBody mealMonthRequest: MealMonthRequest
    ): MealResponse? {
        return connectMeal(URL("${URLList.meal}ATPT_OFCDC_SC_CODE=${mealMonthRequest.cityCode}&SD_SCHUL_CODE=${mealMonthRequest.schoolCode}&MLSV_YMD=$${mealMonthRequest.month}"))
    }
}