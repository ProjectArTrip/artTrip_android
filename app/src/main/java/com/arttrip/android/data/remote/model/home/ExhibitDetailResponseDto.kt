package com.arttrip.android.data.remote.model.home

import kotlinx.serialization.SerialName

data class ExhibitDetailResponseDto(
    @SerialName("exhibit_id")
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
