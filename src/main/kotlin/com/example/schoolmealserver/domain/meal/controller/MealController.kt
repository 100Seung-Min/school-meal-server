package com.example.schoolmealserver.domain.meal.controller

import com.example.schoolmealserver.domain.meal.dto.MealDto
import com.example.schoolmealserver.domain.meal.service.MealService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class MealController(
        private val mealService: MealService
) {
    @GetMapping("/school/meal")
    fun schoolMeal(): MealDto {
        return mealService.getMeal()
    }
}