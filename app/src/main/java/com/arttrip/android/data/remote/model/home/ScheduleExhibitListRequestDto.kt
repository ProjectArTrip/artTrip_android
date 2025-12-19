package com.arttrip.android.data.remote.model.home

sealed interface ScheduleExhibitListRequestDto {
    val isDomestic: Boolean
    val date: String
}

data class ForeignScheduleExhibitListRequestDto(
    override val isDomestic: Boolean = false,
    override val date: String,
    val country: String,
) : ScheduleExhibitListRequestDto

data class DomesticScheduleExhibitListRequestDto(
    override val isDomestic: Boolean = true,
    override val date: String,
    val region: String,
) : ScheduleExhibitListRequestDto