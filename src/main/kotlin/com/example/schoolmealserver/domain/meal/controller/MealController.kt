package com.example.schoolmealserver.domain.meal.controller

import com.example.schoolmealserver.domain.meal.dto.MealDto
import com.example.schoolmealserver.trash.service.MealService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

@RestController
class MealController(
        private val mealService: MealService
) {
    @GetMapping("/school/meal")
    fun schoolMeal(): String {
        val result = StringBuilder()
        val url = URL("https://open.neis.go.kr/hub/mealServiceDietInfo?KEY=dfed562db5ef4e88b1e71079c0039615&Type=json&pIndex=1&pSize=100&ATPT_OFCDC_SC_CODE=T10&SD_SCHUL_CODE=9290083&MLSV_YMD=202211")
        val urlConnection = url.openConnection() as HttpURLConnection
        urlConnection.requestMethod = "GET"
        val br = BufferedReader(InputStreamReader(urlConnection.inputStream, "UTF-8"))
        var returnLine: String?
        do {
            returnLine = br.readLine()
            if(returnLine == null) {
                break
            }
            result.append(returnLine)
        } while (true)
        urlConnection.disconnect()
        return result.toString()
    }
}