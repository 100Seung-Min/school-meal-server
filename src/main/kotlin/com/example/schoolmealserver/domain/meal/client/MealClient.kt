package com.example.schoolmealserver.domain.meal.client

import com.example.schoolmealserver.domain.meal.dto.MealDto
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam

@FeignClient(name = "mealClient", url = "https://open.neis.go.kr/hub/mealServiceDietInfo")
interface MealClient {
    @GetMapping("", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun execute(
            @RequestParam(name = "KEY") key: String,
            @RequestParam(name = "Type") type: String,
            @RequestParam(name = "pIndex") index: String,
            @RequestParam(name = "pSize") size: String,
            @RequestParam(name = "ATPT_OFCDC_SC_CODE") cityCode: String,
            @RequestParam(name = "SD_SCHUL_CODE") schoolCode: String,
            @RequestParam(name = "MLSV_YMD") dateCode: String
    ): MealDto
}