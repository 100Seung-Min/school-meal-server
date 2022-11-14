package com.example.schoolmealserver.domain.school.payload.response

data class TimeResponse(
        val row: List<TimeItem>
) {
    data class TimeItem(
            val time: String,
            val timeName: String
    )
}
