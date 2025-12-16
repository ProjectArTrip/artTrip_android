package com.arttrip.android.data.remote.mapper.exhibit

import com.arttrip.android.data.remote.model.home.ExhibitDetailResponseDto
import com.arttrip.android.domain.model.exhibit.ExhibitDetailModel

fun ExhibitDetailResponseDto.toDomain(): ExhibitDetailModel =
    ExhibitDetailModel(
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
    )
