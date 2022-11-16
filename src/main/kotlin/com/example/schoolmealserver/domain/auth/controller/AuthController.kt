package com.example.schoolmealserver.domain.auth.controller

import com.example.schoolmealserver.domain.auth.payload.request.LoginRequest
import com.example.schoolmealserver.domain.auth.payload.request.PhoneRequest
import com.example.schoolmealserver.domain.auth.payload.request.SignUpRequest
import com.example.schoolmealserver.domain.auth.service.AuthService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.Random

@RestController
@RequestMapping("/auth")
class AuthController(
        private val authService: AuthService
){
    @GetMapping("/signUp")
    fun signUp(
            @RequestBody signUpRequest: SignUpRequest
    ) {
        authService.signUp(signUpRequest)
    }
    @GetMapping("/login")
    fun login(
            @RequestBody loginRequest: LoginRequest
    ): Boolean {
        return authService.login(loginRequest)
    }
    @GetMapping("/phone")
    fun phone(
            @RequestParam("phone") phone: String
    ): String {
        val rand = Random()
        var certificateNumber = ""
        for (i in 0..4) {
            certificateNumber += rand.nextInt(10).toString()
        }
        authService.certificatePhone(phone, certificateNumber)
        return certificateNumber
    }
}