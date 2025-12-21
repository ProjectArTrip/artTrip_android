package com.arttrip.android.domain.repository

import com.arttrip.android.domain.model.home.ExhibitModel
import com.arttrip.android.domain.model.network.ApiResult
import com.arttrip.android.presentation.home.DomesticRegion
import com.arttrip.android.presentation.home.ExhibitGenre
import com.arttrip.android.presentation.home.ForeignCountry
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface HomeRepository {
    fun getForeignRecommendExhibitList(country: ForeignCountry): Flow<ApiResult<List<ExhibitModel>>>

    fun getForeignPersonalizedExhibitList(country: ForeignCountry): Flow<ApiResult<List<ExhibitModel>>>

    fun getForeignScheduleExhibitList(
        country: ForeignCountry, date: LocalDate
    ): Flow<ApiResult<List<ExhibitModel>>>

    fun getForeignGenreExhibitList(
        country: ForeignCountry, genre: ExhibitGenre
    ): Flow<ApiResult<List<ExhibitModel>>>

    fun getDomesticRecommendExhibitList(region: DomesticRegion): Flow<ApiResult<List<ExhibitModel>>>

    fun getDomesticPersonalizedExhibitList(region: DomesticRegion): Flow<ApiResult<List<ExhibitModel>>>

    fun getDomesticScheduleExhibitList(
        region: DomesticRegion, date: LocalDate
    ): Flow<ApiResult<List<ExhibitModel>>>

    fun getDomesticGenreExhibitList(
        region: DomesticRegion, genre: ExhibitGenre
    ): Flow<ApiResult<List<ExhibitModel>>>
}
