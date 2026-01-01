package com.arttrip.android.data.remote.model.home

import com.google.gson.annotations.SerializedName

data class DomesticExhibitResponseDto(
    @SerializedName("exhibit_id")
    val exhibitId: Int,
    val title: String,
    val posterUrl: String,
    val status: String,
    val exhibitPeriod: String,
    val hallName: String,
    val regionName: String,
    val favorite: Boolean,
)
