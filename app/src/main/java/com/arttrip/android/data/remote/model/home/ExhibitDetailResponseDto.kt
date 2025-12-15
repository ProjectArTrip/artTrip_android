package com.arttrip.android.data.remote.model.home

data class ExhibitDetailResponseDto(
    val exhibitId: Long,
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
)
