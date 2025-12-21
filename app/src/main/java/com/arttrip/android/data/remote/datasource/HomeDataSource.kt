package com.arttrip.android.data.remote.datasource

import com.arttrip.android.data.remote.api.HomeApi
import com.arttrip.android.data.remote.model.home.DomesticGenreExhibitListRequestDto
import com.arttrip.android.data.remote.model.home.DomesticPersonalizedExhibitListRequestDto
import com.arttrip.android.data.remote.model.home.DomesticRecommendExhibitListRequestDto
import com.arttrip.android.data.remote.model.home.DomesticScheduleExhibitListRequestDto
import com.arttrip.android.data.remote.model.home.ForeignGenreExhibitListRequestDto
import com.arttrip.android.data.remote.model.home.ForeignPersonalizedExhibitListRequestDto
import com.arttrip.android.data.remote.model.home.ForeignRecommendExhibitListRequestDto
import com.arttrip.android.data.remote.model.home.ForeignScheduleExhibitListRequestDto
import javax.inject.Inject

class HomeDataSource
    @Inject
    constructor(
        private val api: HomeApi,
    ) {
        suspend fun getHomeRecommendToday(requestDto: ForeignRecommendExhibitListRequestDto) =
            api.getHomeRecommendToday(requestDto = requestDto)

        suspend fun getHomeRecommendToday(requestDto: DomesticRecommendExhibitListRequestDto) =
            api.getHomeRecommendToday(requestDto = requestDto)

        suspend fun getHomePersonalizedRandom(requestDto: ForeignPersonalizedExhibitListRequestDto) =
            api.getHomePersonalizedRandom(requestDto = requestDto)

        suspend fun getHomePersonalizedRandom(requestDto: DomesticPersonalizedExhibitListRequestDto) =
            api.getHomePersonalizedRandom(requestDto = requestDto)

        suspend fun getHomeSchedule(requestDto: ForeignScheduleExhibitListRequestDto) = api.getHomeSchedule(requestDto = requestDto)

        suspend fun getHomeSchedule(requestDto: DomesticScheduleExhibitListRequestDto) = api.getHomeSchedule(requestDto = requestDto)

        suspend fun getHomeGenreRandom(requestDto: ForeignGenreExhibitListRequestDto) = api.getHomeGenreRandom(requestDto = requestDto)

        suspend fun getHomeGenreRandom(requestDto: DomesticGenreExhibitListRequestDto) = api.getHomeGenreRandom(requestDto = requestDto)
    }
