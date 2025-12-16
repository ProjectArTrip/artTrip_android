package com.arttrip.android.data.remote.mapper.home

import com.arttrip.android.data.remote.model.home.ExhibitResponseDto
import com.arttrip.android.domain.model.home.ExhibitModel
import com.arttrip.android.domain.model.home.ExhibitStatus

fun List<ExhibitResponseDto>.toDomain(): List<ExhibitModel> = this.map { it.toDomain() }

fun ExhibitResponseDto.toDomain(): ExhibitModel =
    ExhibitModel(
        id = id,
        title = title,
        posterUrl = posterUrl ?: "https://i.pinimg.com/originals/5d/90/1f/5d901f30a1ee270123e19b1404165113.jpg",
        status = status.toExhibitStatus(),
        exhibitPeriod = exhibitPeriod,
    )

fun String.toExhibitStatus(): ExhibitStatus =
    when (this.uppercase()) {
        "ONGOING" -> ExhibitStatus.ONGOING
        "UPCOMING" -> ExhibitStatus.UPCOMING
        "DEADLINE" -> ExhibitStatus.DEADLINE
        else -> ExhibitStatus.DEADLINE
    }
