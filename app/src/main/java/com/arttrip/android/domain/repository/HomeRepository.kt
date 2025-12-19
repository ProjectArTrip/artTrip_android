package com.arttrip.android.domain.repository

import ExhibitListQueryModel
import com.arttrip.android.domain.model.home.ExhibitModel
import com.arttrip.android.domain.model.network.ApiResult
import com.arttrip.android.presentation.home.ExhibitGenre
import com.arttrip.android.presentation.home.Place
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface HomeRepository {
    fun getHomeRecommendExhibitList(place: Place): Flow<ApiResult<List<ExhibitModel>>>

    fun getHomePersonalizedExhibitList(place: Place): Flow<ApiResult<List<ExhibitModel>>>

    fun getHomeScheduleExhibitList(
        place: Place, date: LocalDate
    ): Flow<ApiResult<List<ExhibitModel>>>

    fun getHomeGenreExhibitList(
        place: Place, genre: ExhibitGenre
    ): Flow<ApiResult<List<ExhibitModel>>>
}
