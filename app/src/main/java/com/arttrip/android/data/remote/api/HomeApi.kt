package com.arttrip.android.data.remote.api

import com.arttrip.android.data.remote.api.ApiConstants.HOME_PATH
import com.arttrip.android.data.remote.model.home.DomesticExhibitResponseDto
import com.arttrip.android.data.remote.model.home.DomesticGenreExhibitListRequestDto
import com.arttrip.android.data.remote.model.home.DomesticPersonalizedExhibitListRequestDto
import com.arttrip.android.data.remote.model.home.DomesticRecommendExhibitListRequestDto
import com.arttrip.android.data.remote.model.home.DomesticScheduleExhibitListRequestDto
import com.arttrip.android.data.remote.model.home.ForeignExhibitResponseDto
import com.arttrip.android.data.remote.model.home.ForeignGenreExhibitListRequestDto
import com.arttrip.android.data.remote.model.home.ForeignPersonalizedExhibitListRequestDto
import com.arttrip.android.data.remote.model.home.ForeignRecommendExhibitListRequestDto
import com.arttrip.android.data.remote.model.home.ForeignScheduleExhibitListRequestDto
import com.arttrip.android.data.remote.model.network.BaseResponseDto
import retrofit2.http.Body
import retrofit2.http.POST

interface HomeApi {
    @POST("${HOME_PATH}/recommend/today")
    suspend fun getHomeRecommendToday(
        @Body requestDto: ForeignRecommendExhibitListRequestDto
    ): BaseResponseDto<List<ForeignExhibitResponseDto>>

    @POST("${HOME_PATH}/recommend/today")
    suspend fun getHomeRecommendToday(
        @Body requestDto: DomesticRecommendExhibitListRequestDto
    ): BaseResponseDto<List<DomesticExhibitResponseDto>>

    @POST("${HOME_PATH}/personalized/random")
    suspend fun getHomePersonalizedRandom(
        @Body requestDto: ForeignPersonalizedExhibitListRequestDto
    ): BaseResponseDto<List<ForeignExhibitResponseDto>>

    @POST("${HOME_PATH}/personalized/random")
    suspend fun getHomePersonalizedRandom(
        @Body requestDto: DomesticPersonalizedExhibitListRequestDto
    ): BaseResponseDto<List<DomesticExhibitResponseDto>>

    @POST("${HOME_PATH}/schedule")
    suspend fun getHomeSchedule(
        @Body requestDto: ForeignScheduleExhibitListRequestDto
    ): BaseResponseDto<List<ForeignExhibitResponseDto>>

    @POST("${HOME_PATH}/schedule")
    suspend fun getHomeSchedule(
        @Body requestDto: DomesticScheduleExhibitListRequestDto
    ): BaseResponseDto<List<DomesticExhibitResponseDto>>

    @POST("${HOME_PATH}/genre/random")
    suspend fun getHomeGenreRandom(
        @Body requestDto: ForeignGenreExhibitListRequestDto
    ): BaseResponseDto<List<ForeignExhibitResponseDto>>

    @POST("${HOME_PATH}/genre/random")
    suspend fun getHomeGenreRandom(
        @Body requestDto: DomesticGenreExhibitListRequestDto
    ): BaseResponseDto<List<DomesticExhibitResponseDto>>
}
