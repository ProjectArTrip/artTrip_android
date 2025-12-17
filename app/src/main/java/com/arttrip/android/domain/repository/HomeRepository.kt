package com.arttrip.android.domain.repository

import ExhibitListQueryModel
import com.arttrip.android.domain.model.home.ExhibitModel
import com.arttrip.android.domain.model.network.ApiResult
import com.arttrip.android.presentation.home.Place
import kotlinx.coroutines.flow.Flow

interface HomeRepository {
    fun getHomeRecommendExhibitList(place: Place): Flow<ApiResult<List<ExhibitModel>>>

    fun getHomePersonalizedExhibitList(query: ExhibitListQueryModel): Flow<ApiResult<List<ExhibitModel>>>

    fun getHomeScheduleExhibitList(
        query: ExhibitListQueryModel
    ): Flow<ApiResult<List<ExhibitModel>>>

    fun getHomeGenreExhibitList(
        query: ExhibitListQueryModel
    ): Flow<ApiResult<List<ExhibitModel>>>
}
