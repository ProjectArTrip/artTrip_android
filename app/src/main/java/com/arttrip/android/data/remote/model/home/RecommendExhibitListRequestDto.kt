package com.arttrip.android.data.remote.model.home

data class ForeignRecommendExhibitListRequestDto(
    val isDomestic: Boolean = false,
    val country: String,
)

data class DomesticRecommendExhibitListRequestDto(
    val isDomestic: Boolean = true,
    val region: String,
)
