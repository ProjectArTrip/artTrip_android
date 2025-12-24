package com.arttrip.android.data.remote.mapper.exhibit

import com.arttrip.android.core.model.enums.exhibition.ExhibitionStatus
import com.arttrip.android.data.remote.mapper.home.toExhibitStatus
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
        status = status.toExhibitStatus(),
        hallName = hallName,
        hallAddress = hallAddress,
        hallOpeningHours = hallOpeningHours,
        hallPhone = hallPhone,
        hallLatitude = hallLatitude,
        hallLongitude = hallLongitude,
        isBookmarked = favorite,
    )

fun String.toExhibitStatus(): ExhibitionStatus =
    when (this.uppercase()) {
        "UPCOMING" -> ExhibitionStatus.UPCOMING
        "ONGOING" -> ExhibitionStatus.ONGOING
        "ENDING_SOON" -> ExhibitionStatus.ENDING_SOON
        "FINISHED" -> ExhibitionStatus.FINISHED
        else -> ExhibitionStatus.FINISHED
    }
