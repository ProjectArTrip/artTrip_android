package com.arttrip.app.data.remote.mapper.exhibit

import com.arttrip.app.core.model.enums.exhibition.ExhibitionStatus
import com.arttrip.app.data.remote.model.exhibit.ExhibitDetailResponseDto
import com.arttrip.app.domain.model.exhibition.ExhibitionDetail

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
