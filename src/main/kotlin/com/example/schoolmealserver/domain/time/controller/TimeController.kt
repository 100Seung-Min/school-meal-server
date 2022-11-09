package com.example.schoolmealserver.domain.time.controller

import com.example.schoolmealserver.domain.time.payload.response.TimeReponse
import com.example.schoolmealserver.global.openapi.URLList
import com.example.schoolmealserver.global.openapi.connectTime
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.net.URL
import java.text.SimpleDateFormat
import java.util.*

@RestController
@RequestMapping("/time")
class TimeController {

    private val dayOfWeek = Calendar.getInstance().get(Calendar.DAY_OF_WEEK)
    private val startDay = SimpleDateFormat("yyyyMMdd").format(Date()).toInt() + (1 - dayOfWeek)
    private val endDay = SimpleDateFormat("yyyyMMdd").format(Date()).toInt() + (7 - dayOfWeek)

    @GetMapping("/his")
    fun timeHis(
            @RequestParam(name = "cityCode") cityCode: String,
            @RequestParam(name = "schoolCode") schoolCode: String,
            @RequestParam(name = "grade") grade: String,
            @RequestParam("class") `class`: String
    ): TimeReponse? {
        return connectTime(URL("${URLList.hisTime}ATPT_OFCDC_SC_CODE=$cityCode&SD_SCHUL_CODE=$schoolCode&TI_FROM_YMD=$startDay&TI_TO_YMD=$endDay&GRADE=$grade&CLASS_NM=$`class`"), "his")
    }

    @GetMapping("/mis")
    fun timeMis(
            @RequestParam(name = "cityCode") cityCode: String,
            @RequestParam(name = "schoolCode") schoolCode: String,
            @RequestParam(name = "grade") grade: String,
            @RequestParam("class") `class`: String
    ): TimeReponse? {
        return connectTime(URL("${URLList.misTime}ATPT_OFCDC_SC_CODE=$cityCode&SD_SCHUL_CODE=$schoolCode&TI_FROM_YMD=$startDay&TI_TO_YMD=$endDay&GRADE=$grade&CLASS_NM=$`class`"), "mis")
    }

    @GetMapping("/els")
    fun timeEls(
            @RequestParam(name = "cityCode") cityCode: String,
            @RequestParam(name = "schoolCode") schoolCode: String,
            @RequestParam(name = "grade") grade: String,
            @RequestParam("class") `class`: String
    ): TimeReponse? {
        return connectTime(URL("${URLList.elsTime}ATPT_OFCDC_SC_CODE=$cityCode&SD_SCHUL_CODE=$schoolCode&TI_FROM_YMD=$startDay&TI_TO_YMD=$endDay&GRADE=$grade&CLASS_NM=$`class`"), "els")
    }
}