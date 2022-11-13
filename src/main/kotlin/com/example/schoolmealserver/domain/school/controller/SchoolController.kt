package com.example.schoolmealserver.domain.school.controller

import com.example.schoolmealserver.domain.auth.service.AuthService
import com.example.schoolmealserver.domain.school.payload.response.MealResponse
import com.example.schoolmealserver.domain.school.payload.response.ScheduleResponse
import com.example.schoolmealserver.domain.school.payload.response.TimeReponse
import com.example.schoolmealserver.global.openapi.URLList
import com.example.schoolmealserver.global.openapi.connectMeal
import com.example.schoolmealserver.global.openapi.connectSchedule
import com.example.schoolmealserver.global.openapi.connectTime
import org.springframework.web.bind.annotation.*
import java.net.URL
import java.text.SimpleDateFormat
import java.util.*

@RestController
@RequestMapping("/school")
class SchoolController(
        private val authService: AuthService
) {
    private val dayOfWeek = Calendar.getInstance().get(Calendar.DAY_OF_WEEK)
    private val startDay = SimpleDateFormat("yyyyMMdd").format(Date()).toInt() + (1 - dayOfWeek)
    private val endDay = SimpleDateFormat("yyyyMMdd").format(Date()).toInt() + (7 - dayOfWeek)

    @GetMapping("/schedule")
    fun schedule(
            @RequestHeader("id") id: String,
            @RequestParam(name = "month") month: String
    ): ScheduleResponse? {
        return connectSchedule(URL("${URLList.schedule}ATPT_OFCDC_SC_CODE=${authService.getUser(id).cityCode}&SD_SCHUL_CODE=${authService.getUser(id).schoolCode}&AA_YMD=$month"))
    }

    @GetMapping("/meal")
    fun meal(
            @RequestHeader("id") id: String,
            @RequestParam("day") day: String
    ): MealResponse? {
        return connectMeal(URL("${URLList.meal}ATPT_OFCDC_SC_CODE=${authService.getUser(id).cityCode}&SD_SCHUL_CODE=${authService.getUser(id).schoolCode}&MLSV_YMD=${day}"))
    }

    @GetMapping("/meal/month")
    fun mealMonth(
            @RequestHeader("id") id: String,
            @RequestParam("month") month: String
    ): MealResponse? {
        return connectMeal(URL("${URLList.meal}ATPT_OFCDC_SC_CODE=${authService.getUser(id).cityCode}&SD_SCHUL_CODE=${authService.getUser(id).schoolCode}&MLSV_YMD=$${month}"))
    }

    @GetMapping("/time/his")
    fun timeHis(
            @RequestParam(name = "cityCode") cityCode: String,
            @RequestParam(name = "schoolCode") schoolCode: String,
            @RequestParam(name = "grade") grade: String,
            @RequestParam("class") `class`: String
    ): TimeReponse? {
        return connectTime(URL("${URLList.hisTime}ATPT_OFCDC_SC_CODE=$cityCode&SD_SCHUL_CODE=$schoolCode&TI_FROM_YMD=$startDay&TI_TO_YMD=$endDay&GRADE=$grade&CLASS_NM=$`class`"), "his")
    }

    @GetMapping("/time/mis")
    fun timeMis(
            @RequestParam(name = "cityCode") cityCode: String,
            @RequestParam(name = "schoolCode") schoolCode: String,
            @RequestParam(name = "grade") grade: String,
            @RequestParam("class") `class`: String
    ): TimeReponse? {
        return connectTime(URL("${URLList.misTime}ATPT_OFCDC_SC_CODE=$cityCode&SD_SCHUL_CODE=$schoolCode&TI_FROM_YMD=$startDay&TI_TO_YMD=$endDay&GRADE=$grade&CLASS_NM=$`class`"), "mis")
    }

    @GetMapping("/time/els")
    fun timeEls(
            @RequestParam(name = "cityCode") cityCode: String,
            @RequestParam(name = "schoolCode") schoolCode: String,
            @RequestParam(name = "grade") grade: String,
            @RequestParam("class") `class`: String
    ): TimeReponse? {
        return connectTime(URL("${URLList.elsTime}ATPT_OFCDC_SC_CODE=$cityCode&SD_SCHUL_CODE=$schoolCode&TI_FROM_YMD=$startDay&TI_TO_YMD=$endDay&GRADE=$grade&CLASS_NM=$`class`"), "els")
    }
}