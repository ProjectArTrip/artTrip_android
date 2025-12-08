package com.arttrip.android.domain.repository

import com.arttrip.android.domain.model.home.ExhibitModel
import com.arttrip.android.domain.model.network.ApiResult
import kotlinx.coroutines.flow.Flow

interface HomeRepository {
    fun getCountryList(): Flow<ApiResult<List<String>>>

    fun getHomeRecommendExhibitList(isDomestic: Boolean): Flow<ApiResult<List<ExhibitModel>>>
}
