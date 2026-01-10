package com.arttrip.android.data.remote.mapper.favorite

import com.arttrip.android.data.remote.model.favorite.AddFavoriteResDto
import com.arttrip.android.domain.model.bookmark.BookmarkResult

fun AddFavoriteResDto.toDomain(): BookmarkResult =
    BookmarkResult(
        favoriteId = favoriteId,
        exhibitId = exhibitId,
    )
