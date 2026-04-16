package com.arttrip.app.data.remote.mapper.home

import com.arttrip.app.core.model.enums.domestic.DomesticRegion
import com.arttrip.app.core.model.enums.exhibition.ExhibitionGenre
import com.arttrip.app.core.model.enums.exhibition.ExhibitionStatus
import com.arttrip.app.core.model.enums.foreign.ForeignCountry
import com.arttrip.app.data.remote.model.home.DomesticExhibitListResponseDto
import com.arttrip.app.data.remote.model.home.DomesticExhibitResponseDto
import com.arttrip.app.data.remote.model.home.DomesticGenreExhibitListRequestDto
import com.arttrip.app.data.remote.model.home.DomesticPersonalizedExhibitListRequestDto
import com.arttrip.app.data.remote.model.home.DomesticRecommendExhibitListRequestDto
import com.arttrip.app.data.remote.model.home.DomesticScheduleExhibitListRequestDto
import com.arttrip.app.data.remote.model.home.ForeignExhibitListResponseDto
import com.arttrip.app.data.remote.model.home.ForeignExhibitResponseDto
import com.arttrip.app.data.remote.model.home.ForeignGenreExhibitListRequestDto
import com.arttrip.app.data.remote.model.home.ForeignPersonalizedExhibitListRequestDto
import com.arttrip.app.data.remote.model.home.ForeignRecommendExhibitListRequestDto
import com.arttrip.app.data.remote.model.home.ForeignScheduleExhibitListRequestDto
import com.arttrip.app.domain.model.exhibition.Exhibition
import java.time.LocalDate
import java.time.format.DateTimeFormatter

fun ForeignExhibitListResponseDto.toForeignDomain(): List<Exhibition> = this.exhibits.map { it.toDomain() }

fun ForeignExhibitResponseDto.toDomain(): Exhibition =
    Exhibition(
        id = exhibitId,
        title = title,
        posterUrl = posterUrl,
        status = status.toExhibitStatus(),
        period = exhibitPeriod,
        hallName = hallName,
        country = countryName,
        region = regionName,
        isBookmarked = isFavorite,
    )

fun DomesticExhibitListResponseDto.toDomesticDomain(): List<Exhibition> = this.exhibits.map { it.toDomain() }

fun DomesticExhibitResponseDto.toDomain(): Exhibition =
    Exhibition(
        id = exhibitId,
        title = title,
        posterUrl = posterUrl,
        status = status.toExhibitStatus(),
        period = exhibitPeriod,
        hallName = hallName,
        country = countryName,
        region = regionName,
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

fun ForeignCountry.toRecommendRequestDto(): ForeignRecommendExhibitListRequestDto =
    ForeignRecommendExhibitListRequestDto(
        country = this.label,
    )

fun ForeignCountry.toPersonalizedRequestDto(): ForeignPersonalizedExhibitListRequestDto =
    ForeignPersonalizedExhibitListRequestDto(
        country = this.label,
    )

fun ForeignCountry.toGenreRequestDto(genre: ExhibitionGenre): ForeignGenreExhibitListRequestDto =
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

fun DomesticRegion.toGenreRequestDto(genre: ExhibitionGenre): DomesticGenreExhibitListRequestDto =
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
