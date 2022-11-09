package com.example.schoolmealserver.domain.schedule.payload.response

data class ScheduleResponse(
        val row: List<ScheduleItem>
) {
    data class ScheduleItem(
            val event: String,
            val date: String
    )
}
