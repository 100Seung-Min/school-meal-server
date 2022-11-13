package com.example.schoolmealserver.domain.schedule.controller

import com.example.schoolmealserver.domain.auth.service.AuthService
import com.example.schoolmealserver.domain.schedule.payload.response.ScheduleResponse
import com.example.schoolmealserver.global.openapi.URLList
import com.example.schoolmealserver.global.openapi.connectSchedule
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.net.URL

@RestController
class ScheduleController(
        private val authService: AuthService
) {
    @GetMapping("/schedule")
    fun schedule(
            @RequestHeader("id") id: String,
            @RequestParam(name = "month") month: String
    ): ScheduleResponse? {
        return connectSchedule(URL("${URLList.schedule}ATPT_OFCDC_SC_CODE=${authService.getUser(id).cityCode}&SD_SCHUL_CODE=${authService.getUser(id).schoolCode}&AA_YMD=$month"))
    }
}