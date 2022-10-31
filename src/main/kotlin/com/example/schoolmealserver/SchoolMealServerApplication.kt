package com.example.schoolmealserver

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients

@EnableFeignClients
@SpringBootApplication
class SchoolMealServerApplication

fun main(args: Array<String>) {
    runApplication<SchoolMealServerApplication>(*args)
 }
