package com.example.schoolmealserver.domain.auth.payload.request

data class SignUpRequest(
        val id: String,
        val password: String,
        val phone: String,
        val schoolName: String,
        val `class`: String,
        val grade: String,
        val name: String
)
