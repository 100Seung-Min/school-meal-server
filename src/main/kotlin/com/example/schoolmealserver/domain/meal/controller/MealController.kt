package com.example.schoolmealserver.domain.meal.controller

import com.example.schoolmealserver.domain.meal.dto.MealDto
import com.example.schoolmealserver.domain.meal.request.MealMonthRequest
import com.example.schoolmealserver.domain.meal.request.MealRequest
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

@RestController
class MealController {
    @GetMapping("/meal")
    fun meal(
            @RequestBody mealRequest: MealRequest
    ): MealDto? {
        return connectMeal(URL("https://open.neis.go.kr/hub/mealServiceDietInfo?KEY=dfed562db5ef4e88b1e71079c0039615&Type=json&pIndex=1&pSize=100&ATPT_OFCDC_SC_CODE=${mealRequest.cityCode}&SD_SCHUL_CODE=${mealRequest.schoolCode}&MLSV_YMD=${mealRequest.day}"))
    }

    @GetMapping("/meal/month")
    fun mealMonth(
            @RequestBody mealMonthRequest: MealMonthRequest
    ): MealDto? {
        return connectMeal(URL("https://open.neis.go.kr/hub/mealServiceDietInfo?KEY=dfed562db5ef4e88b1e71079c0039615&Type=json&pIndex=1&pSize=100&ATPT_OFCDC_SC_CODE=${mealMonthRequest.cityCode}&SD_SCHUL_CODE=${mealMonthRequest.schoolCode}&MLSV_YMD=$${mealMonthRequest.month}"))
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