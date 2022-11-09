package com.example.schoolmealserver.domain.auth.payload.request

data class LoginRequest(
        val id: String,
        val password: String
)
