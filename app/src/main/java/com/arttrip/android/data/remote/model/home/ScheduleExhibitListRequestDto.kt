package com.arttrip.android.data.remote.model.home

data class ForeignScheduleExhibitListRequestDto(
    val isDomestic: Boolean = false,
    val date: String,
    val country: String,
)

data class DomesticScheduleExhibitListRequestDto(
    val isDomestic: Boolean = true,
    val date: String,
    val region: String,
)