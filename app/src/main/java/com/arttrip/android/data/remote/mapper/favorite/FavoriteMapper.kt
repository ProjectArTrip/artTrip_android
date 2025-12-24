package com.arttrip.android.data.remote.mapper.favorite

import com.arttrip.android.data.remote.model.favorite.AddFavoriteResDto
import com.arttrip.android.domain.model.bookmark.BookmarkResultModel

fun AddFavoriteResDto.toDomain(): BookmarkResultModel =
    BookmarkResultModel(
        favoriteId = favoriteId,
        exhibitId = exhibitId,
    )
