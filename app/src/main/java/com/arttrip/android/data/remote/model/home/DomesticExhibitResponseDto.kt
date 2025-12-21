package com.arttrip.android.data.remote.model.home

import kotlinx.serialization.SerialName

data class DomesticExhibitResponseDto(
    @SerialName("exhibit_id")
    val id: Int,
    val title: String,
    val posterUrl: String,
    val status: String,
    val exhibitPeriod: String,
    val hallName: String,
    val regionName: String,
)