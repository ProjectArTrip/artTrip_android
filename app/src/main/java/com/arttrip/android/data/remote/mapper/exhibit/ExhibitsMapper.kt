package com.arttrip.android.data.remote.mapper.exhibit

import com.arttrip.android.data.remote.model.home.ForeignExhibitResponseDto
import com.arttrip.android.domain.model.exhibition.Exhibition

fun ForeignExhibitResponseDto.toDomain(): Exhibition =
    Exhibition(
        id = exhibitId,
        title = title,
        posterUrl = posterUrl ?: "",
        status = status.toExhibitStatus(),
        period = exhibitPeriod,
        hallName = hallName,
        country = countryName,
        region = regionName,
        isBookmarked = isFavorite,
    )
