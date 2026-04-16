package com.arttrip.app.data.remote.model.home

data class ForeignExhibitListResponseDto(
    val exhibits: List<ForeignExhibitResponseDto>,
)

data class ForeignExhibitResponseDto(
    val exhibitId: Int,
    val title: String,
    val posterUrl: String,
    val status: String,
    val exhibitPeriod: String,
    val hallName: String,
    val countryName: String,
    val regionName: String,
    val isFavorite: Boolean,
)
