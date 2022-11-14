package com.example.schoolmealserver.domain.school.payload.response

data class SchoolResponse(
        val row: List<SchoolItem>
) {
    data class SchoolItem(
            val schoolName: String,
            val cityCode: String,
            val schoolCode: String
    )
}