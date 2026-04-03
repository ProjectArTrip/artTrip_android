package com.arttrip.android.data.remote.mapper.favorite

import com.arttrip.android.core.model.enums.exhibition.ExhibitionConstants
import com.arttrip.android.data.remote.mapper.exhibit.toExhibitStatus
import com.arttrip.android.data.remote.model.favorite.FavoriteResDto
import com.arttrip.android.domain.model.favorite.Bookmark

fun FavoriteResDto.toDomain(): Bookmark =
    Bookmark(
        favoriteId = favoriteId,
        exhibitId = exhibitId,
        title = title,
        posterUrl = posterUrl,
        status = exhibitStatus.toExhibitStatus(),
        period = exhibitPeriod,
        hallName = exhibitHallName,
        location = if (country == ExhibitionConstants.DOMESTIC_COUNTRY) region else country,
    )
