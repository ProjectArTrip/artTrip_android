package com.arttrip.android.data.remote.model.home

data class ExhibitListRequestDto(
    val isDomestic: Boolean,
    val country: String? = null,
    val region: String? = null,
    val singleGenre: String? = null,
    val genres: List<String> = emptyList(),
    val styles: List<String> = emptyList(),
    val date: String,
    val limit: Int = 0,
)