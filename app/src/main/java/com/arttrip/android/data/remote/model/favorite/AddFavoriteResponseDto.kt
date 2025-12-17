package com.arttrip.android.data.remote.model.favorite

data class AddFavoriteResponseDto(
    val favoriteId: Int,
    val exhibitId: Int,
    val title: String,
    val posterUrl: String?,
    val status: String,
    val exhibitPeriod: String,
    val exhibitHallName: String,
    val country: String,
    val region: String,
    val createdAt: String,
)
