package com.example.schoolmealserver.domain.time.dto

data class TimeDto(
        val row: List<TimeItem>
) {
    data class TimeItem(
            val time: String,
            val timeName: String
    )
}
