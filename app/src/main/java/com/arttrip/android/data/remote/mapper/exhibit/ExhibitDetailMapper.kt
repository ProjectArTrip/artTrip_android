package com.arttrip.android.data.remote.mapper.exhibit

import com.arttrip.android.data.remote.model.home.ExhibitDetailResponseDto
import com.arttrip.android.domain.model.exhibition.ExhibitionDetailModel

fun ExhibitDetailResponseDto.toDomain(): ExhibitionDetailModel =
    ExhibitionDetailModel(
        exhibitId = exhibitId,
        title = title,
        description = description,
        posterUrl = posterUrl,
        ticketUrl = ticketUrl,
        exhibitPeriod = exhibitPeriod,
        hallName = hallName,
        hallAddress = hallAddress,
        hallOpeningHours = hallOpeningHours,
        hallPhone = hallPhone,
        hallLatitude = hallLatitude,
        hallLongitude = hallLongitude,
        isBookmarked = favorite,
    )
