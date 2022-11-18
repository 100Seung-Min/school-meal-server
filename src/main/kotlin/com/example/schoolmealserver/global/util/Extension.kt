package com.example.schoolmealserver.global.util

fun String.removeDot(): String {
    return this.replace("^\"|\"$".toRegex(), "")
}