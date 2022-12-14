package com.example.schoolmealserver.domain.school.controller

import com.example.schoolmealserver.domain.auth.service.AuthService
import com.example.schoolmealserver.domain.school.payload.response.MealResponse
import com.example.schoolmealserver.domain.school.payload.response.ScheduleResponse
import com.example.schoolmealserver.domain.school.payload.response.SchoolResponse
import com.example.schoolmealserver.domain.school.payload.response.TimeResponse
import com.example.schoolmealserver.global.openapi.*
import org.springframework.web.bind.annotation.*
import java.net.URL
import java.net.URLEncoder
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

    @GetMapping
    fun school(
            @RequestParam(name = "schoolName") schoolName: String
    ): List<SchoolResponse>? {
        return connect(URL("${URLList.school}SCHUL_NM=${URLEncoder.encode(schoolName, "UTF-8")}"), "schoolInfo")
    }

    @GetMapping("/schedule")
    fun schedule(
            @RequestHeader("id") id: String,
            @RequestParam(name = "month") month: String
    ): List<ScheduleResponse>? {
        val user = authService.getUser(id)
        return connect(URL("${URLList.schedule}ATPT_OFCDC_SC_CODE=${user.cityCode}&SD_SCHUL_CODE=${user.schoolCode}&AA_YMD=$month"), "SchoolSchedule")
    }

    @GetMapping("/meal")
    fun meal(
            @RequestHeader("id") id: String,
            @RequestParam("day") day: String
    ): List<MealResponse>? {
        val user = authService.getUser(id)
        return connect( URL("${URLList.meal}ATPT_OFCDC_SC_CODE=${user.cityCode}&SD_SCHUL_CODE=${user.schoolCode}&MLSV_YMD=${day}"), "mealServiceDietInfo")
    }

    @GetMapping("/meal/month")
    fun mealMonth(
            @RequestHeader("id") id: String,
            @RequestParam("month") month: String
    ): List<MealResponse>? {
        val user = authService.getUser(id)
        return connect(URL("${URLList.meal}ATPT_OFCDC_SC_CODE=${user.cityCode}&SD_SCHUL_CODE=${user.schoolCode}&MLSV_YMD=${month}"), "mealServiceDietInfo")
    }

    @GetMapping("/time")
    fun timeHis(
            @RequestHeader("id") id: String
    ): List<TimeResponse>? {
        val user = authService.getUser(id)
        when(user.schoolClass) {
            "????????????", "????????????????????????" -> return connect(URL("${URLList.hisTime}ATPT_OFCDC_SC_CODE=${user.cityCode}&SD_SCHUL_CODE=${user.schoolCode}&TI_FROM_YMD=$startDay&TI_TO_YMD=$endDay&GRADE=${user.grade}&CLASS_NM=${user.`class`}"), "hisTimetable")
            "?????????", "?????????????????????" -> return connect(URL("${URLList.misTime}ATPT_OFCDC_SC_CODE=${user.cityCode}&SD_SCHUL_CODE=${user.schoolCode}&TI_FROM_YMD=$startDay&TI_TO_YMD=$endDay&GRADE=${user.grade}&CLASS_NM=${user.`class`}"), "misTimetable")
            "????????????" -> return connect(URL("${URLList.elsTime}ATPT_OFCDC_SC_CODE=${user.cityCode}&SD_SCHUL_CODE=${user.schoolCode}&TI_FROM_YMD=$startDay&TI_TO_YMD=$endDay&GRADE=${user.grade}&CLASS_NM=${user.`class`}"), "elsTimetable")
            "????????????" -> return connect(URL("${URLList.elsTime}ATPT_OFCDC_SC_CODE=${user.cityCode}&SD_SCHUL_CODE=${user.schoolCode}&TI_FROM_YMD=$startDay&TI_TO_YMD=$endDay&GRADE=${user.grade}&CLASS_NM=${user.`class`}"), "spsTimetable")
        }
        return null
    }
}