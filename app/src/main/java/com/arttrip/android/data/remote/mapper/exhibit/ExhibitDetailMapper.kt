package com.arttrip.android.data.remote.mapper.exhibit

import com.arttrip.android.core.model.enums.exhibition.ExhibitionStatus
import com.arttrip.android.domain.model.exhibition.ExhibitionDetail
import com.arttrip.android.data.remote.model.exhibit.ExhibitDetailResponseDto

fun ExhibitDetailResponseDto.toDomain(): ExhibitionDetail =
    ExhibitionDetail(
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
        isBookmarked = isFavorite,
    )

fun String.toExhibitStatus(): ExhibitionStatus =
    when (this.uppercase()) {
        "UPCOMING" -> ExhibitionStatus.UPCOMING
        "ONGOING" -> ExhibitionStatus.ONGOING
        "ENDING_SOON" -> ExhibitionStatus.ENDING_SOON
        "FINISHED" -> ExhibitionStatus.FINISHED
        else -> ExhibitionStatus.FINISHED
    }
