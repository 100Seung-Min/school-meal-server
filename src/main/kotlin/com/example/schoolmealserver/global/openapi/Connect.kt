package com.example.schoolmealserver.global.openapi

import com.example.schoolmealserver.domain.school.payload.response.MealResponse
import com.example.schoolmealserver.domain.school.payload.response.ScheduleResponse
import com.example.schoolmealserver.domain.school.payload.response.TimeReponse
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

fun connectSchedule(url: URL): ScheduleResponse? {
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


private fun parseJson(jsonData: String): ScheduleResponse? {
    var response: ScheduleResponse? = null
    try {
        val jsonParser = JsonParser()
        val jsonObject = jsonParser.parse(jsonData) as JsonObject
        val jsonResponse = jsonObject["SchoolSchedule"] as JsonArray
        val jsonRow = jsonResponse.get(1) as JsonObject
        val jsonData = jsonRow["row"] as JsonArray
        var array = listOf<ScheduleResponse.ScheduleItem>()
        jsonData.forEach {it as JsonObject
            val item = ScheduleResponse.ScheduleItem(
                    it["EVENT_NM"].toString(),
                    it["AA_YMD"].toString()
            )
            if(item.event != "\"토요휴업일\"") {
                array = array.plus(item)
            }
        }
        response = ScheduleResponse(array)

    } catch (e: Exception) {
        e.printStackTrace()
    }
    return response
}



fun connectMeal(url: URL): MealResponse? {
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
    return parseJsonMeal(result.toString())
}

private fun parseJsonMeal(jsonData: String): MealResponse? {
    var response: MealResponse? = null
    try {
        val jsonParser = JsonParser()
        val jsonObject = jsonParser.parse(jsonData) as JsonObject
        val jsonResponse = jsonObject["mealServiceDietInfo"] as JsonArray
        val jsonRow = jsonResponse.get(1) as JsonObject
        val jsonData = jsonRow["row"] as JsonArray
        var array = listOf<MealResponse.MealItem>()
        jsonData.forEach {it as JsonObject
            val item = MealResponse.MealItem(
                    it["DDISH_NM"].toString(),
                    it["MLSV_YMD"].toString(),
                    it["MMEAL_SC_NM"].toString()
            )
            array = array.plus(item)
        }
        response = MealResponse(array)

    } catch (e: Exception) {
        e.printStackTrace()
    }
    return response
}


fun connectTime(url: URL, type: String): TimeReponse? {
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
            if (it["ITRT_CNTNT"].toString() != "\"토요휴업일\"") {
                val item = TimeReponse.TimeItem(
                        it["PERIO"].toString(),
                        it["ITRT_CNTNT"].toString(),
                )
                array = array.plus(item)
            }
        }
        response = TimeReponse(array)

    } catch (e: Exception) {
        e.printStackTrace()
    }
    return response
}