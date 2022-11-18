package com.example.schoolmealserver.global.openapi

import com.example.schoolmealserver.domain.school.payload.response.MealResponse
import com.example.schoolmealserver.domain.school.payload.response.ScheduleResponse
import com.example.schoolmealserver.domain.school.payload.response.SchoolResponse
import com.example.schoolmealserver.domain.school.payload.response.TimeResponse
import com.example.schoolmealserver.global.util.removeDot
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

fun <T> connect(url: URL, type: String): List<T> {
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
    var response = listOf<T>()
    try {
        val jsonParser = JsonParser()
        val jsonObject = jsonParser.parse(result.toString()) as JsonObject
        val jsonResponse = jsonObject[type] as JsonArray
        val jsonRow = jsonResponse.get(1) as JsonObject
        val jsonData = jsonRow["row"] as JsonArray
        when (type) {
            "schoolInfo" -> {
                jsonData.forEach {it as JsonObject
                    val item = SchoolResponse.SchoolItem(
                            it["SCHUL_NM"].toString().removeDot(),
                            it["ATPT_OFCDC_SC_CODE"].toString().removeDot(),
                            it["SD_SCHUL_CODE"].toString().removeDot()
                    )
                    response = response.plus(item as T)
                }
            }
            "SchoolSchedule" -> {
                jsonData.forEach {it as JsonObject
                    if(it["EVENT_NM"].toString() != "\"토요휴업일\"") {
                        val item = ScheduleResponse.ScheduleItem(
                                it["EVENT_NM"].toString().removeDot(),
                                it["AA_YMD"].toString().removeDot()
                        )
                        response = response.plus(item as T)
                    }
                }
            }
            "mealServiceDietInfo" -> {
                jsonData.forEach {it as JsonObject
                    val item = MealResponse.MealItem(
                            it["DDISH_NM"].toString().removeDot(),
                            it["MLSV_YMD"].toString().removeDot(),
                            it["MMEAL_SC_NM"].toString().removeDot()
                    )
                    response = response.plus(item as T)
                }
            }
            else -> {
                jsonData.forEach {it as JsonObject
                    if (it["ITRT_CNTNT"].toString() != "\"토요휴업일\"") {
                        val item = TimeResponse.TimeItem(
                                it["PERIO"].toString().removeDot(),
                                it["ITRT_CNTNT"].toString().removeDot(),
                        )
                        response = response.plus(item as T)
                    }
                }
            }
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return response
}