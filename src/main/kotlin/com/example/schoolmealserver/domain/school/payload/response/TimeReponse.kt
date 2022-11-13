package com.example.schoolmealserver.domain.school.payload.response

data class TimeReponse(
        val row: List<TimeItem>
) {
    data class TimeItem(
            val time: String,
            val timeName: String
    )
}
