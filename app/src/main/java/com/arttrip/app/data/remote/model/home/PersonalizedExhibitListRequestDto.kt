package com.arttrip.app.data.remote.model.home

data class ForeignPersonalizedExhibitListRequestDto(
    val isDomestic: Boolean = false,
    val country: String,
)

data class DomesticPersonalizedExhibitListRequestDto(
    val isDomestic: Boolean = true,
    val region: String,
)
