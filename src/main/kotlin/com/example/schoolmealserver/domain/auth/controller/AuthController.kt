package com.example.schoolmealserver.domain.auth.controller

import com.example.schoolmealserver.domain.auth.service.AuthService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.Random

@RestController
@RequestMapping("/auth")
class AuthController {
    @GetMapping("signUp")
    fun signUp(

    ): Boolean {
        return true
    }
    @GetMapping("/phone")
    fun phone(
            @RequestParam(name = "phone") phone: String
    ): String {
        val rand = Random()
        var certificateNumber = ""
        for (i in 0..4) {
            certificateNumber += rand.nextInt(10).toString()
        }
        AuthService().certificatePhone(phone, certificateNumber)
        return certificateNumber
    }
}