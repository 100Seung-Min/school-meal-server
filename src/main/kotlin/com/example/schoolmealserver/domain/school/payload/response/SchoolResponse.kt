package com.example.schoolmealserver.domain.school.payload.response

data class SchoolResponse(
        val schoolName: String,
        val cityCode: String,
        val schoolCode: String,
        val schoolClass: String
)