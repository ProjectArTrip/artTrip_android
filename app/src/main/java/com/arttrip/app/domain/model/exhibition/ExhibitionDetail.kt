package com.arttrip.app.domain.model.exhibition

import com.arttrip.app.core.model.enums.exhibition.ExhibitionStatus

data class ExhibitionDetail(
    val exhibitId: Int,
    val title: String,
    val description: String?,
    val posterUrl: String?,
    val ticketUrl: String?,
    val exhibitPeriod: String,
    val status: ExhibitionStatus,
    val hallName: String?,
    val hallAddress: String?,
    val hallOpeningHours: String?,
    val hallPhone: String?,
    val hallLatitude: Double?,
    val hallLongitude: Double?,
    val isBookmarked: Boolean,
)
