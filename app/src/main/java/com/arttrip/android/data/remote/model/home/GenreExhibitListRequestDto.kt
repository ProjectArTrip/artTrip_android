package com.arttrip.android.data.remote.model.home

sealed interface GenreExhibitListRequestDto {
    val isDomestic: Boolean
    val singleGenre: String
}

data class ForeignGenreExhibitListRequestDto(
    override val isDomestic: Boolean = false,
    override val singleGenre: String,
    val country: String,
) : GenreExhibitListRequestDto

data class DomesticGenreExhibitListRequestDto(
    override val isDomestic: Boolean = true,
    override val singleGenre: String,
    val region: String,
) : GenreExhibitListRequestDto