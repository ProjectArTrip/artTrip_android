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
        suspend fun getHomeRecommendToday(
            requestDto: ForeignRecommendExhibitListRequestDto,
            width: Int,
            height: Int,
            format: String,
        ) = api.getHomeRecommendToday(requestDto = requestDto, width = width, height = height, format = format)

        suspend fun getHomeRecommendToday(
            requestDto: DomesticRecommendExhibitListRequestDto,
            width: Int,
            height: Int,
            format: String,
        ) = api.getHomeRecommendToday(requestDto = requestDto, width = width, height = height, format = format)

        suspend fun getHomePersonalizedRandom(
            requestDto: ForeignPersonalizedExhibitListRequestDto,
            width: Int,
            height: Int,
            format: String,
        ) = api.getHomePersonalizedRandom(requestDto = requestDto, width = width, height = height, format = format)

        suspend fun getHomePersonalizedRandom(
            requestDto: DomesticPersonalizedExhibitListRequestDto,
            width: Int,
            height: Int,
            format: String,
        ) = api.getHomePersonalizedRandom(requestDto = requestDto, width = width, height = height, format = format)

        suspend fun getHomeSchedule(
            requestDto: ForeignScheduleExhibitListRequestDto,
            width: Int,
            height: Int,
            format: String,
        ) = api.getHomeSchedule(requestDto = requestDto, width = width, height = height, format = format)

        suspend fun getHomeSchedule(
            requestDto: DomesticScheduleExhibitListRequestDto,
            width: Int,
            height: Int,
            format: String,
        ) = api.getHomeSchedule(requestDto = requestDto, width = width, height = height, format = format)

        suspend fun getHomeGenreRandom(
            requestDto: ForeignGenreExhibitListRequestDto,
            width: Int,
            height: Int,
            format: String,
        ) = api.getHomeGenreRandom(requestDto = requestDto, width = width, height = height, format = format)

        suspend fun getHomeGenreRandom(
            requestDto: DomesticGenreExhibitListRequestDto,
            width: Int,
            height: Int,
            format: String,
        ) = api.getHomeGenreRandom(requestDto = requestDto, width = width, height = height, format = format)
    }
