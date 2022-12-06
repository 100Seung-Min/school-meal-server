package com.example.schoolmealserver.domain.auth.payload.request

data class SignUpRequest(
        val id: String,
        val password: String,
        val phone: String,
        val cityCode: String,
        val schoolName: String,
        val schoolCode: String,
        val `class`: String,
        val grade: String,
        val name: String,
        val schoolClass: String
)
