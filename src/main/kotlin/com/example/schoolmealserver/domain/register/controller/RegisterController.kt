package com.example.schoolmealserver.domain.register.controller

import com.example.schoolmealserver.global.service.CertificationService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.Random

@RestController
@RequestMapping("/register")
class RegisterController {
    @GetMapping("/phone")
    fun phone(
            @RequestParam(name = "phone") phone: String
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