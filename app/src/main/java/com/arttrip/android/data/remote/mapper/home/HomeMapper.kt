package com.arttrip.android.data.remote.mapper.home

import com.arttrip.android.core.model.enums.domestic.DomesticRegion
import com.arttrip.android.core.model.enums.exhibition.ExhibitionGenre
import com.arttrip.android.core.model.enums.exhibition.ExhibitionStatus
import com.arttrip.android.core.model.enums.foreign.ForeignCountry
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
import com.arttrip.android.domain.model.exhibition.Exhibition
import java.time.LocalDate
import java.time.format.DateTimeFormatter

fun List<ForeignExhibitResponseDto>.toForeignDomain(): List<Exhibition> = this.map { it.toDomain() }

fun ForeignExhibitResponseDto.toDomain(): Exhibition =
    Exhibition(
        id = exhibitId,
        title = title,
        posterUrl = posterUrl,
        status = status.toExhibitStatus(),
        period = exhibitPeriod,
        hallName = hallName,
        place = countryName ?: "",
        isBookmarked = favorite,
    )

fun List<DomesticExhibitResponseDto>.toDomesticDomain(): List<Exhibition> = this.map { it.toDomain() }

fun DomesticExhibitResponseDto.toDomain(): Exhibition =
    Exhibition(
        id = exhibitId,
        title = title,
        posterUrl = posterUrl,
        status = status.toExhibitStatus(),
        period = exhibitPeriod,
        hallName = hallName,
        place = regionName,
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
