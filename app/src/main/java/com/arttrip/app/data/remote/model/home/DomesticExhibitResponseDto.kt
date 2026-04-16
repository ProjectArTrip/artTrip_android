package com.arttrip.app.data.remote.model.home

data class DomesticExhibitListResponseDto(
    val exhibits: List<DomesticExhibitResponseDto>,
)

data class DomesticExhibitResponseDto(
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
