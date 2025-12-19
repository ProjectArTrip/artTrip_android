package com.arttrip.android.data.remote.mapper.home

import DomesticExhibitListQueryModel
import ExhibitListQueryModel
import ForeignExhibitListQueryModel
import com.arttrip.android.data.remote.model.home.DomesticExhibitListRequestDto
import com.arttrip.android.data.remote.model.home.DomesticGenreExhibitListRequestDto
import com.arttrip.android.data.remote.model.home.DomesticScheduleExhibitListRequestDto
import com.arttrip.android.data.remote.model.home.ExhibitListRequestDto
import com.arttrip.android.data.remote.model.home.ExhibitResponseDto
import com.arttrip.android.data.remote.model.home.ForeignExhibitListRequestDto
import com.arttrip.android.data.remote.model.home.ForeignGenreExhibitListRequestDto
import com.arttrip.android.data.remote.model.home.ForeignScheduleExhibitListRequestDto
import com.arttrip.android.data.remote.model.home.GenreExhibitListRequestDto
import com.arttrip.android.data.remote.model.home.RecommendExhibitListRequestDto
import com.arttrip.android.data.remote.model.home.ScheduleExhibitListRequestDto
import com.arttrip.android.domain.model.home.ExhibitModel
import com.arttrip.android.domain.model.home.ExhibitStatus
import com.arttrip.android.presentation.home.DomesticRegion
import com.arttrip.android.presentation.home.ExhibitGenre
import com.arttrip.android.presentation.home.ForeignCountry
import com.arttrip.android.presentation.home.Place
import java.time.LocalDate
import java.time.format.DateTimeFormatter

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
        "UPCOMING" -> ExhibitStatus.UPCOMING
        "ONGOING" -> ExhibitStatus.ONGOING
        "ENDING_SOON" -> ExhibitStatus.ENDING_SOON
        "FINISHED" -> ExhibitStatus.FINISHED
        else -> ExhibitStatus.FINISHED
    }

fun ExhibitListQueryModel.toRequestDto(): ExhibitListRequestDto =
    when (this) {
        is ForeignExhibitListQueryModel -> ExhibitListRequestDto(
            isDomestic = false,
            country = country,
            region = null,
            singleGenre = singleGenre,
            genres = genres,
            styles = styles,
            date = date,
            limit = limit,
        )

        is DomesticExhibitListQueryModel -> ExhibitListRequestDto(
            isDomestic = true,
            country = null,
            region = region,
            singleGenre = singleGenre,
            genres = genres,
            styles = styles,
            date = date,
            limit = limit,
        )
    }

fun Place.toRequestDto(): RecommendExhibitListRequestDto =
    when (this) {
        is ForeignCountry -> ForeignExhibitListRequestDto(
            country = this.label
        )

        is DomesticRegion -> DomesticExhibitListRequestDto(
            region = this.label
        )
    }

fun Place.toGenreRequestDto(genre: ExhibitGenre): GenreExhibitListRequestDto =
    when (this) {
        is ForeignCountry -> ForeignGenreExhibitListRequestDto(
            singleGenre = genre.label,
            country = this.label
        )

        is DomesticRegion -> DomesticGenreExhibitListRequestDto(
            singleGenre = genre.label,
            region = this.label
        )
    }

fun Place.toScheduleRequestDto(date: LocalDate): ScheduleExhibitListRequestDto {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

    val dateString = date.format(formatter)

    return when (this) {
        is ForeignCountry -> ForeignScheduleExhibitListRequestDto(
            date = dateString,
            country = this.label
        )

        is DomesticRegion -> DomesticScheduleExhibitListRequestDto(
            date = dateString,
            region = this.label
        )
    }
}
