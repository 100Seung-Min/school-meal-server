package com.example.schoolmealserver.domain.meal.controller

import com.example.schoolmealserver.domain.auth.service.AuthService
import com.example.schoolmealserver.domain.meal.payload.response.MealResponse
import com.example.schoolmealserver.global.openapi.URLList
import com.example.schoolmealserver.global.openapi.connectMeal
import org.springframework.web.bind.annotation.*
import java.net.URL

@RestController
@RequestMapping("/meal")
class MealController(
        private val authService: AuthService
) {
    @GetMapping
    fun meal(
            @RequestHeader("id") id: String,
            @RequestParam("day") day: String
    ): MealResponse? {
        return connectMeal(URL("${URLList.meal}ATPT_OFCDC_SC_CODE=${authService.getUser(id).cityCode}&SD_SCHUL_CODE=${authService.getUser(id).schoolCode}&MLSV_YMD=${day}"))
    }

    @GetMapping("/month")
    fun mealMonth(
            @RequestHeader("id") id: String,
            @RequestParam("month") month: String
    ): MealResponse? {
        return connectMeal(URL("${URLList.meal}ATPT_OFCDC_SC_CODE=${authService.getUser(id).cityCode}&SD_SCHUL_CODE=${authService.getUser(id).schoolCode}&MLSV_YMD=$${month}"))
    }
}