package com.arttrip.android.data.remote.mapper.favorite

import com.arttrip.android.data.remote.model.favorite.AddFavoriteResDto
import com.arttrip.android.data.remote.model.favorite.FavoriteCheckResDto
import com.arttrip.android.domain.model.bookmark.BookmarkCheckModel
import com.arttrip.android.domain.model.bookmark.BookmarkResultModel

fun AddFavoriteResDto.toDomain(): BookmarkResultModel =
    BookmarkResultModel(
        favoriteId = favoriteId,
        exhibitId = exhibitId,
    )

fun FavoriteCheckResDto.toDomain(): BookmarkCheckModel =
    BookmarkCheckModel(
        isBookmarked = isFavorite,
    )
