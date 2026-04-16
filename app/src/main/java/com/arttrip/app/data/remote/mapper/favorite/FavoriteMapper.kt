package com.arttrip.app.data.remote.mapper.favorite

import com.arttrip.app.core.model.enums.exhibition.ExhibitionConstants
import com.arttrip.app.data.remote.mapper.exhibit.toExhibitStatus
import com.arttrip.app.data.remote.model.favorite.FavoriteResDto
import com.arttrip.app.domain.model.favorite.Bookmark

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
