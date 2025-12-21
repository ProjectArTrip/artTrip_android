package com.arttrip.android.domain.repository

import com.arttrip.android.core.model.enums.domestic.DomesticRegion
import com.arttrip.android.core.model.enums.exhibition.ExhibitionGenre
import com.arttrip.android.core.model.enums.foreign.ForeignCountry
import com.arttrip.android.domain.model.exhibition.ExhibitionModel
import com.arttrip.android.domain.model.network.ApiResult
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface HomeRepository {
    fun getForeignRecommendExhibitList(country: ForeignCountry): Flow<ApiResult<List<ExhibitionModel>>>

    fun getForeignPersonalizedExhibitList(country: ForeignCountry): Flow<ApiResult<List<ExhibitionModel>>>

    fun getForeignScheduleExhibitList(
        country: ForeignCountry,
        date: LocalDate,
    ): Flow<ApiResult<List<ExhibitionModel>>>

    fun getForeignGenreExhibitList(
        country: ForeignCountry,
        genre: ExhibitionGenre,
    ): Flow<ApiResult<List<ExhibitionModel>>>

    fun getDomesticRecommendExhibitList(region: DomesticRegion): Flow<ApiResult<List<ExhibitionModel>>>

    fun getDomesticPersonalizedExhibitList(region: DomesticRegion): Flow<ApiResult<List<ExhibitionModel>>>

    fun getDomesticScheduleExhibitList(
        region: DomesticRegion,
        date: LocalDate,
    ): Flow<ApiResult<List<ExhibitionModel>>>

    fun getDomesticGenreExhibitList(
        region: DomesticRegion,
        genre: ExhibitionGenre,
    ): Flow<ApiResult<List<ExhibitionModel>>>
}
