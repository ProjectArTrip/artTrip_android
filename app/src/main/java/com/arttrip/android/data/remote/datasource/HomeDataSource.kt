package com.arttrip.android.data.remote.datasource

import com.arttrip.android.data.remote.api.HomeApi
import com.arttrip.android.data.remote.model.home.ExhibitListRequestDto
import javax.inject.Inject

class HomeDataSource
    @Inject
    constructor(
        private val api: HomeApi,
    ) {
        suspend fun getHomeRecommendToday(requestDto: ExhibitListRequestDto) = api.getHomeRecommendToday(requestDto = requestDto)

        suspend fun getHomePersonalizedRandom(requestDto: ExhibitListRequestDto) = api.getHomePersonalizedRandom(requestDto = requestDto)

        suspend fun getHomeSchedule(requestDto: ExhibitListRequestDto) = api.getHomeSchedule(requestDto = requestDto)

    suspend fun getHomeGenreRandom(requestDto: ExhibitListRequestDto) = api.getHomeGenreRandom(requestDto = requestDto)
    }
