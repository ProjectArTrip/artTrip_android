package com.arttrip.app.data.remote.datasource

import com.arttrip.app.data.remote.api.HomeApi
import com.arttrip.app.data.remote.model.home.DomesticGenreExhibitListRequestDto
import com.arttrip.app.data.remote.model.home.DomesticPersonalizedExhibitListRequestDto
import com.arttrip.app.data.remote.model.home.DomesticRecommendExhibitListRequestDto
import com.arttrip.app.data.remote.model.home.DomesticScheduleExhibitListRequestDto
import com.arttrip.app.data.remote.model.home.ForeignGenreExhibitListRequestDto
import com.arttrip.app.data.remote.model.home.ForeignPersonalizedExhibitListRequestDto
import com.arttrip.app.data.remote.model.home.ForeignRecommendExhibitListRequestDto
import com.arttrip.app.data.remote.model.home.ForeignScheduleExhibitListRequestDto
import javax.inject.Inject

class HomeDataSource
    @Inject
    constructor(
        private val api: HomeApi,
    ) {
        suspend fun getHomeRecommendToday(requestDto: ForeignRecommendExhibitListRequestDto) =
            api.getForeignHomeRecommendToday(isDomestic = requestDto.isDomestic, country = requestDto.country)

        suspend fun getHomeRecommendToday(requestDto: DomesticRecommendExhibitListRequestDto) =
            api.getDomesticHomeRecommendToday(isDomestic = requestDto.isDomestic, region = requestDto.region)

        suspend fun getHomePersonalizedRandom(requestDto: ForeignPersonalizedExhibitListRequestDto) =
            api.getForeignHomePersonalizedRandom(isDomestic = requestDto.isDomestic, country = requestDto.country)

        suspend fun getHomePersonalizedRandom(requestDto: DomesticPersonalizedExhibitListRequestDto) =
            api.getDomesticHomePersonalizedRandom(isDomestic = requestDto.isDomestic, region = requestDto.region)

        suspend fun getHomeSchedule(requestDto: ForeignScheduleExhibitListRequestDto) =
            api.getForeignHomeSchedule(isDomestic = requestDto.isDomestic, date = requestDto.date, country = requestDto.country)

        suspend fun getHomeSchedule(requestDto: DomesticScheduleExhibitListRequestDto) =
            api.getDomesticHomeSchedule(isDomestic = requestDto.isDomestic, date = requestDto.date, region = requestDto.region)

        suspend fun getHomeGenreRandom(requestDto: ForeignGenreExhibitListRequestDto) =
            api.getForeignHomeGenreRandom(
                isDomestic = requestDto.isDomestic,
                singleGenre = requestDto.singleGenre,
                country = requestDto.country,
            )

        suspend fun getHomeGenreRandom(requestDto: DomesticGenreExhibitListRequestDto) =
            api.getDomesticHomeGenreRandom(
                isDomestic = requestDto.isDomestic,
                singleGenre = requestDto.singleGenre,
                region = requestDto.region,
            )
    }
