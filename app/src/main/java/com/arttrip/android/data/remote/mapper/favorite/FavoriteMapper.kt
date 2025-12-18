package com.arttrip.android.data.remote.mapper.favorite

import com.arttrip.android.data.remote.model.favorite.AddFavoriteResponseDto
import com.arttrip.android.data.remote.model.favorite.FavoriteCheckResponseDto
import com.arttrip.android.domain.model.favorite.FavoriteCheckModel
import com.arttrip.android.domain.model.favorite.FavoriteResult

fun AddFavoriteResponseDto.toDomain(): FavoriteResult =
    FavoriteResult(
        favoriteId = favoriteId,
        exhibitId = exhibitId,
    )

fun FavoriteCheckResponseDto.toDomain(): FavoriteCheckModel =
    FavoriteCheckModel(
        isFavorite = isFavorite,
    )
