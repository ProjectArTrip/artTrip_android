package com.arttrip.app.data.remote.model.exhibit

data class ExhibitDetailResponseDto(
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
    val hallLatitude: Double,
    val hallLongitude: Double,
    val isFavorite: Boolean,
)
