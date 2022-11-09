package com.example.schoolmealserver.domain.schedule.controller

import com.example.schoolmealserver.domain.schedule.payload.response.ScheduleResponse
import com.example.schoolmealserver.global.openapi.URLList
import com.example.schoolmealserver.global.openapi.connectSchedule
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.net.URL

@RestController
class ScheduleController {
    @GetMapping("/schedule")
    fun schedule(
            @RequestParam(name = "cityCode") cityCode: String,
            @RequestParam(name = "schoolCode") schoolCode: String,
            @RequestParam(name = "month") month: String
    ): ScheduleResponse? {
        return connectSchedule(URL("${URLList.schedule}ATPT_OFCDC_SC_CODE=$cityCode&SD_SCHUL_CODE=$schoolCode&AA_YMD=$month"))
    }
}