package com.arttrip.android.data.remote.datasource

import com.arttrip.android.data.remote.api.HomeApi
import com.arttrip.android.data.remote.model.home.DomesticExhibitListRequestDto
import com.arttrip.android.data.remote.model.home.ExhibitListRequestDto
import com.arttrip.android.data.remote.model.home.ForeignExhibitListRequestDto
import com.arttrip.android.data.remote.model.home.RecommendExhibitListRequestDto
import javax.inject.Inject

class HomeDataSource
    @Inject
    constructor(
        private val api: HomeApi,
    ) {
        suspend fun getHomeRecommendToday(requestDto: ForeignExhibitListRequestDto) = api.getHomeRecommendToday(requestDto = requestDto)

    suspend fun getHomeRecommendToday(requestDto: DomesticExhibitListRequestDto) = api.getHomeRecommendToday(requestDto = requestDto)

    suspend fun getHomePersonalizedRandom(requestDto: ForeignExhibitListRequestDto) = api.getHomePersonalizedRandom(requestDto = requestDto)

        suspend fun getHomePersonalizedRandom(requestDto: DomesticExhibitListRequestDto) = api.getHomePersonalizedRandom(requestDto = requestDto)

        suspend fun getHomeSchedule(requestDto: ExhibitListRequestDto) = api.getHomeSchedule(requestDto = requestDto)

    suspend fun getHomeGenreRandom(requestDto: ExhibitListRequestDto) = api.getHomeGenreRandom(requestDto = requestDto)
    }
