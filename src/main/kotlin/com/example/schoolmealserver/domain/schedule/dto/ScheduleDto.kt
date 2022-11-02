package com.example.schoolmealserver.domain.schedule.dto

data class ScheduleDto(
        val row: List<ScheduleItem>
) {
    data class ScheduleItem(
            val event: String,
            val date: String
    )
}
