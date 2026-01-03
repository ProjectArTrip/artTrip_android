package com.arttrip.android.data.remote.model.home

import com.google.gson.annotations.SerializedName

data class ExhibitDetailResponseDto(
    @SerializedName("exhibit_id")
    val exhibitId: Int,
    val title: String,
    val description: String,
    val posterUrl: String?,
    val ticketUrl: String?,
    val exhibitPeriod: String,
    val status: String,
    val hallName: String,
    val hallAddress: String?,
    val hallOpeningHours: String?,
    val hallPhone: String?,
    val hallLatitude: Double?,
    val hallLongitude: Double?,
    val favorite: Boolean,
)
