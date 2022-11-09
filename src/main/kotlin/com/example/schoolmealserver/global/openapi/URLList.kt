package com.example.schoolmealserver.global.openapi

object URLList {
    private const val defaultParam = "KEY=dfed562db5ef4e88b1e71079c0039615&Type=json&pIndex=1&pSize=100&"
    const val schedule = "https://open.neis.go.kr/hub/SchoolSchedule?$defaultParam"
    const val meal = "https://open.neis.go.kr/hub/mealServiceDietInfo?$defaultParam"
    const val hisTime = "https://open.neis.go.kr/hub/hisTimetable?$defaultParam"
    const val misTime = "https://open.neis.go.kr/hub/misTimetable?$defaultParam"
    const val elsTime = "https://open.neis.go.kr/hub/elsTimetable?$defaultParam"
}