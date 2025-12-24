package com.arttrip.android.domain.model.exhibition

data class ExhibitionDetailModel(
    val exhibitId: Int,
    val title: String,
    val description: String?,
    val posterUrl: String?,
    val ticketUrl: String?,
    val exhibitPeriod: String,
    val hallName: String?,
    val hallAddress: String?,
    val hallOpeningHours: String?,
    val hallPhone: String?,
    val hallLatitude: Double?,
    val hallLongitude: Double?,
    val isBookmarked: Boolean,
)
