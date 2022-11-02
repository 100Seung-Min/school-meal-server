package com.example.schoolmealserver.domain.register.controller

import com.example.schoolmealserver.global.service.CertificationService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.Random

@RestController
class RegisterController {
    @GetMapping("register/phone")
    fun phone(
            @RequestParam("phone") phone: String
    ): String {
        val rand = Random()
        var certificateNumber = ""
        for (i in 0..4) {
            certificateNumber += rand.nextInt(10).toString()
        }
        CertificationService().certificatePhone(phone, certificateNumber)
        return certificateNumber
    }
}