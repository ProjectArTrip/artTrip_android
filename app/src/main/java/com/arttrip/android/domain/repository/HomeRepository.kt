package com.arttrip.android.domain.repository

import ExhibitListQueryModel
import com.arttrip.android.domain.model.home.ExhibitModel
import com.arttrip.android.domain.model.network.ApiResult
import kotlinx.coroutines.flow.Flow

interface HomeRepository {
    fun getCountryList(): Flow<ApiResult<List<String>>>

    fun getHomeRecommendExhibitList(query: ExhibitListQueryModel): Flow<ApiResult<List<ExhibitModel>>>

    fun getHomePersonalizedExhibitList(isDomestic: Boolean): Flow<ApiResult<List<ExhibitModel>>>

    fun getHomeScheduleExhibitList(
        isDomestic: Boolean,
        date: String,
    ): Flow<ApiResult<List<ExhibitModel>>>
}
