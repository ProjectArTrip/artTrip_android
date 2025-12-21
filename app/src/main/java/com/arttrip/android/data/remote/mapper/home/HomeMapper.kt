package com.arttrip.android.data.remote.mapper.home

import com.arttrip.android.core.model.enum.exhibit.ExhibitStatus
import com.arttrip.android.data.remote.model.home.DomesticExhibitResponseDto
import com.arttrip.android.data.remote.model.home.DomesticGenreExhibitListRequestDto
import com.arttrip.android.data.remote.model.home.DomesticPersonalizedExhibitListRequestDto
import com.arttrip.android.data.remote.model.home.DomesticRecommendExhibitListRequestDto
import com.arttrip.android.data.remote.model.home.DomesticScheduleExhibitListRequestDto
import com.arttrip.android.data.remote.model.home.ForeignExhibitResponseDto
import com.arttrip.android.data.remote.model.home.ForeignGenreExhibitListRequestDto
import com.arttrip.android.data.remote.model.home.ForeignPersonalizedExhibitListRequestDto
import com.arttrip.android.data.remote.model.home.ForeignRecommendExhibitListRequestDto
import com.arttrip.android.data.remote.model.home.ForeignScheduleExhibitListRequestDto
import com.arttrip.android.domain.model.exhibition.ExhibitionModel
import com.arttrip.android.presentation.home.DomesticRegion
import com.arttrip.android.presentation.home.ExhibitGenre
import com.arttrip.android.presentation.home.ForeignCountry
import java.time.LocalDate
import java.time.format.DateTimeFormatter

fun List<ForeignExhibitResponseDto>.toForeignDomain(): List<ExhibitionModel> = this.map { it.toDomain() }

fun ForeignExhibitResponseDto.toDomain(): ExhibitionModel =
    ExhibitionModel(
        id = id,
        title = title,
        posterUrl = posterUrl,
        status = status.toExhibitStatus(),
        period = exhibitPeriod,
        hallName = hallName,
        place = countryName,
    )

fun List<DomesticExhibitResponseDto>.toDomesticDomain(): List<ExhibitionModel> = this.map { it.toDomain() }

fun DomesticExhibitResponseDto.toDomain(): ExhibitionModel =
    ExhibitionModel(
        id = id,
        title = title,
        posterUrl = posterUrl,
        status = status.toExhibitStatus(),
        period = exhibitPeriod,
        hallName = hallName,
        place = regionName,
    )

fun String.toExhibitStatus(): ExhibitStatus =
    when (this.uppercase()) {
        "UPCOMING" -> ExhibitStatus.UPCOMING
        "ONGOING" -> ExhibitStatus.ONGOING
        "ENDING_SOON" -> ExhibitStatus.ENDING_SOON
        "FINISHED" -> ExhibitStatus.FINISHED
        else -> ExhibitStatus.FINISHED
    }

fun ForeignCountry.toRecommendRequestDto(): ForeignRecommendExhibitListRequestDto =
    ForeignRecommendExhibitListRequestDto(
        country = this.label,
    )

fun ForeignCountry.toPersonalizedRequestDto(): ForeignPersonalizedExhibitListRequestDto =
    ForeignPersonalizedExhibitListRequestDto(
        country = this.label,
    )

fun ForeignCountry.toGenreRequestDto(genre: ExhibitGenre): ForeignGenreExhibitListRequestDto =
    ForeignGenreExhibitListRequestDto(
        singleGenre = genre.label,
        country = this.label,
    )

fun ForeignCountry.toScheduleRequestDto(date: LocalDate): ForeignScheduleExhibitListRequestDto {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    val dateString = date.format(formatter)

    return ForeignScheduleExhibitListRequestDto(
        date = dateString,
        country = this.label,
    )
}

fun DomesticRegion.toRecommendRequestDto(): DomesticRecommendExhibitListRequestDto =
    DomesticRecommendExhibitListRequestDto(
        region = this.label,
    )

fun DomesticRegion.toPersonalizedRequestDto(): DomesticPersonalizedExhibitListRequestDto =
    DomesticPersonalizedExhibitListRequestDto(
        region = this.label,
    )

fun DomesticRegion.toGenreRequestDto(genre: ExhibitGenre): DomesticGenreExhibitListRequestDto =
    DomesticGenreExhibitListRequestDto(
        singleGenre = genre.label,
        region = this.label,
    )

fun DomesticRegion.toScheduleRequestDto(date: LocalDate): DomesticScheduleExhibitListRequestDto {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    val dateString = date.format(formatter)

    return DomesticScheduleExhibitListRequestDto(
        date = dateString,
        region = this.label,
    )
}
