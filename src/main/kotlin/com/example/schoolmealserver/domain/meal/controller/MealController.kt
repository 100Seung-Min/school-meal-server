package com.example.schoolmealserver.domain.meal.controller

import com.example.schoolmealserver.domain.meal.dto.MealDto
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

@RestController
class MealController {
    @GetMapping("/meal")
    fun meal(
            @RequestParam(name = "cityCode") cityCode: String,
            @RequestParam(name = "schoolCode") schoolCode: String,
            @RequestParam(name = "day") day: String
    ): MealDto? {
        return connectMeal(URL("https://open.neis.go.kr/hub/mealServiceDietInfo?KEY=dfed562db5ef4e88b1e71079c0039615&Type=json&pIndex=1&pSize=100&ATPT_OFCDC_SC_CODE=$cityCode&SD_SCHUL_CODE=$schoolCode&MLSV_YMD=$day"))
    }

    @GetMapping("/meal/week")
    fun mealWeek(
            @RequestParam(name = "cityCode") cityCode: String,
            @RequestParam(name = "schoolCode") schoolCode: String,
            @RequestParam(name = "startDay") startDay: String,
            @RequestParam(name = "endDay") endDay: String
    ): MealDto? {
        return connectMeal(URL("https://open.neis.go.kr/hub/mealServiceDietInfo?KEY=dfed562db5ef4e88b1e71079c0039615&Type=json&pIndex=1&pSize=100&ATPT_OFCDC_SC_CODE=$cityCode&SD_SCHUL_CODE=$schoolCode&MLSV_FROM_YMD=$startDay&MLSV_TO_YMD=$endDay"))
    }

    @GetMapping("/meal/month")
    fun mealMonth(
            @RequestParam(name = "cityCode") cityCode: String,
            @RequestParam(name = "schoolCode") schoolCode: String,
            @RequestParam(name = "month") month: String
    ): MealDto? {
        return connectMeal(URL("https://open.neis.go.kr/hub/mealServiceDietInfo?KEY=dfed562db5ef4e88b1e71079c0039615&Type=json&pIndex=1&pSize=100&ATPT_OFCDC_SC_CODE=$cityCode&SD_SCHUL_CODE=$schoolCode&MLSV_YMD=$month"))
    }

    private fun connectMeal(url: URL): MealDto? {
        val result = StringBuilder()
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
        return parseJson(result.toString())
    }

    private fun parseJson(jsonData: String): MealDto? {
        var response: MealDto? = null
        try {
            val jsonParser = JsonParser()
            val jsonObject = jsonParser.parse(jsonData) as JsonObject
            val jsonResponse = jsonObject["mealServiceDietInfo"] as JsonArray
            val jsonRow = jsonResponse.get(1) as JsonObject
            val jsonData = jsonRow["row"] as JsonArray
            var array = listOf<MealDto.MealItem>()
            jsonData.forEach {it as JsonObject
                val item = MealDto.MealItem(
                        it["DDISH_NM"].toString(),
                        it["MLSV_YMD"].toString(),
                        it["MMEAL_SC_NM"].toString()
                )
                array = array.plus(item)
            }
            response = MealDto(array)

        } catch (e: Exception) {
            e.printStackTrace()
        }
        return response
    }
}