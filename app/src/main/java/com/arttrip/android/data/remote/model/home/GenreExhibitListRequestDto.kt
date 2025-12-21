package com.arttrip.android.data.remote.model.home

data class ForeignGenreExhibitListRequestDto(
    val isDomestic: Boolean = false,
    val singleGenre: String,
    val country: String,
)

data class DomesticGenreExhibitListRequestDto(
    val isDomestic: Boolean = true,
    val singleGenre: String,
    val region: String,
)