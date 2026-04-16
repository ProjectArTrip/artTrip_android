package com.arttrip.app.data.remote.mapper.exhibit

import com.arttrip.app.data.remote.model.home.ForeignExhibitResponseDto
import com.arttrip.app.domain.model.exhibition.Exhibition

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
