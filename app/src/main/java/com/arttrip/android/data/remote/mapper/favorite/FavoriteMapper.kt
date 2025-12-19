package com.arttrip.android.data.remote.mapper.favorite

import com.arttrip.android.data.remote.model.favorite.AddFavoriteResponseDto
import com.arttrip.android.data.remote.model.favorite.FavoriteCheckResponseDto
import com.arttrip.android.domain.model.bookmark.BookmarkCheckModel
import com.arttrip.android.domain.model.bookmark.BookmarkResultModel

fun AddFavoriteResponseDto.toDomain(): BookmarkResultModel =
    BookmarkResultModel(
        favoriteId = favoriteId,
        exhibitId = exhibitId,
    )

fun FavoriteCheckResponseDto.toDomain(): BookmarkCheckModel =
    BookmarkCheckModel(
        isFavorite = isFavorite,
    )
