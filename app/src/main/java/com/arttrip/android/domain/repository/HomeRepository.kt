package com.arttrip.android.domain.repository

import com.arttrip.android.core.model.enums.domestic.DomesticRegion
import com.arttrip.android.core.model.enums.exhibition.ExhibitionGenre
import com.arttrip.android.core.model.enums.foreign.ForeignCountry
import com.arttrip.android.domain.model.exhibition.Exhibition
import com.arttrip.android.domain.model.network.ApiResult
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface HomeRepository {
    fun getForeignRecommendExhibitList(country: ForeignCountry): Flow<ApiResult<List<Exhibition>>>

    fun getForeignPersonalizedExhibitList(country: ForeignCountry): Flow<ApiResult<List<Exhibition>>>

    fun getForeignScheduleExhibitList(
        country: ForeignCountry,
        date: LocalDate,
    ): Flow<ApiResult<List<Exhibition>>>

    fun getForeignGenreExhibitList(
        country: ForeignCountry,
        genre: ExhibitionGenre,
    ): Flow<ApiResult<List<Exhibition>>>

    fun getDomesticRecommendExhibitList(region: DomesticRegion): Flow<ApiResult<List<Exhibition>>>

    fun getDomesticPersonalizedExhibitList(region: DomesticRegion): Flow<ApiResult<List<Exhibition>>>

    fun getDomesticScheduleExhibitList(
        region: DomesticRegion,
        date: LocalDate,
    ): Flow<ApiResult<List<Exhibition>>>

    fun getDomesticGenreExhibitList(
        region: DomesticRegion,
        genre: ExhibitionGenre,
    ): Flow<ApiResult<List<Exhibition>>>
}
