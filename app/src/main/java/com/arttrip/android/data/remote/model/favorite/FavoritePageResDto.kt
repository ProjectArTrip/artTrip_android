package com.arttrip.android.data.remote.model.favorite

data class FavoritePageResDto<T>(
    val favorites: List<T> = emptyList(),
    val nextCursor: Int?,
    val hasNext: Boolean,
    val favoriteTotalCount: Int,
)
