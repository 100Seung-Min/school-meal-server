package com.example.schoolmealserver.domain.schedule.controller

import com.example.schoolmealserver.domain.schedule.dto.ScheduleDto
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
class ScheduleController {
    @GetMapping("/schedule")
    fun schedule(
            @RequestParam(name = "cityCode") cityCode: String,
            @RequestParam(name = "schoolCode") schoolCode: String,
            @RequestParam(name = "month") month: String
    ): ScheduleDto? {
        return connectSchedule(URL("https://open.neis.go.kr/hub/SchoolSchedule?KEY=dfed562db5ef4e88b1e71079c0039615&Type=json&pIndex=1&pSize=100&ATPT_OFCDC_SC_CODE=$cityCode&SD_SCHUL_CODE=$schoolCode&AA_YMD=$month"))
    }

    private fun connectSchedule(url: URL): ScheduleDto? {
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

    private fun parseJson(jsonData: String): ScheduleDto? {
        var response: ScheduleDto? = null
        try {
            val jsonParser = JsonParser()
            val jsonObject = jsonParser.parse(jsonData) as JsonObject
            val jsonResponse = jsonObject["SchoolSchedule"] as JsonArray
            val jsonRow = jsonResponse.get(1) as JsonObject
            val jsonData = jsonRow["row"] as JsonArray
            var array = listOf<ScheduleDto.ScheduleItem>()
            jsonData.forEach {it as JsonObject
                val item = ScheduleDto.ScheduleItem(
                        it["EVENT_NM"].toString(),
                        it["AA_YMD"].toString()
                )
                if(item.event != "\"토요휴업일\"") {
                    array = array.plus(item)
                }
            }
            response = ScheduleDto(array)

        } catch (e: Exception) {
            e.printStackTrace()
        }
        return response
    }
}