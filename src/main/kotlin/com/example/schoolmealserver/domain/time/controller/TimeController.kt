package com.example.schoolmealserver.domain.time.controller

import com.example.schoolmealserver.domain.time.payload.response.TimeReponse
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
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
        return connectTime(URL("https://open.neis.go.kr/hub/hisTimetable?KEY=dfed562db5ef4e88b1e71079c0039615&Type=json&pIndex=1&pSize=100&ATPT_OFCDC_SC_CODE=$cityCode&SD_SCHUL_CODE=$schoolCode&TI_FROM_YMD=$startDay&TI_TO_YMD=$endDay&GRADE=$grade&CLASS_NM=$`class`"), "his")
    }

    @GetMapping("/mis")
    fun timeMis(
            @RequestParam(name = "cityCode") cityCode: String,
            @RequestParam(name = "schoolCode") schoolCode: String,
            @RequestParam(name = "grade") grade: String,
            @RequestParam("class") `class`: String
    ): TimeReponse? {
        return connectTime(URL("https://open.neis.go.kr/hub/misTimetable?KEY=dfed562db5ef4e88b1e71079c0039615&Type=json&pIndex=1&pSize=100&ATPT_OFCDC_SC_CODE=$cityCode&SD_SCHUL_CODE=$schoolCode&TI_FROM_YMD=$startDay&TI_TO_YMD=$endDay&GRADE=$grade&CLASS_NM=$`class`"), "mis")
    }

    @GetMapping("/els")
    fun timeEls(
            @RequestParam(name = "cityCode") cityCode: String,
            @RequestParam(name = "schoolCode") schoolCode: String,
            @RequestParam(name = "grade") grade: String,
            @RequestParam("class") `class`: String
    ): TimeReponse? {
        return connectTime(URL("https://open.neis.go.kr/hub/elsTimetable?KEY=dfed562db5ef4e88b1e71079c0039615&Type=json&pIndex=1&pSize=100&ATPT_OFCDC_SC_CODE=$cityCode&SD_SCHUL_CODE=$schoolCode&TI_FROM_YMD=$startDay&TI_TO_YMD=$endDay&GRADE=$grade&CLASS_NM=$`class`"), "els")
    }

    private fun connectTime(url: URL, type: String): TimeReponse? {
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
        return parseJson(result.toString(), type)
    }

    private fun parseJson(jsonData: String, type: String): TimeReponse? {
        var response: TimeReponse? = null
        try {
            val jsonParser = JsonParser()
            val jsonObject = jsonParser.parse(jsonData) as JsonObject
            val jsonResponse = when(type) {
                "his" -> jsonObject["hisTimetable"] as JsonArray
                "mis" -> jsonObject["misTimetable"] as JsonArray
                else -> jsonObject["elsTimetable"] as JsonArray
            }
            val jsonRow = jsonResponse.get(1) as JsonObject
            val jsonData = jsonRow["row"] as JsonArray
            var array = listOf<TimeReponse.TimeItem>()
            jsonData.forEach {it as JsonObject
                val item = TimeReponse.TimeItem(
                        it["PERIO"].toString(),
                        it["ITRT_CNTNT"].toString(),
                )
                array = array.plus(item)
            }
            response = TimeReponse(array)

        } catch (e: Exception) {
            e.printStackTrace()
        }
        return response
    }
}