package com.arttrip.android.data.remote.mapper.favorite

import com.arttrip.android.data.remote.mapper.exhibit.toExhibitStatus
import com.arttrip.android.data.remote.model.favorite.FavoriteResDto
import com.arttrip.android.domain.model.favorite.Favorite

fun FavoriteResDto.toDomain(): Favorite =
    Favorite(
        favoriteId = favoriteId,
        exhibitId = exhibitId,
        title = title,
        posterUrl = posterUrl,
        status = exhibitStatus.toExhibitStatus(),
        period = exhibitPeriod,
        hallName = exhibitHallName,
        country = country,
        region = region,
    )
