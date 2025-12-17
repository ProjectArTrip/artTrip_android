package com.arttrip.android.data.remote.api

import com.arttrip.android.data.remote.api.ApiConstants.HOME_PATH
import com.arttrip.android.data.remote.model.home.DomesticExhibitListRequestDto
import com.arttrip.android.data.remote.model.home.ExhibitListRequestDto
import com.arttrip.android.data.remote.model.home.ExhibitResponseDto
import com.arttrip.android.data.remote.model.home.ForeignExhibitListRequestDto
import com.arttrip.android.data.remote.model.network.BaseResponseDto
import retrofit2.http.Body
import retrofit2.http.POST

interface HomeApi {
    @POST("${HOME_PATH}/recommend/today")
    suspend fun getHomeRecommendToday(
        @Body requestDto: ForeignExhibitListRequestDto
    ): BaseResponseDto<List<ExhibitResponseDto>>

    @POST("${HOME_PATH}/recommend/today")
    suspend fun getHomeRecommendToday(
        @Body requestDto: DomesticExhibitListRequestDto
    ): BaseResponseDto<List<ExhibitResponseDto>>

    @POST("${HOME_PATH}/personalized/random")
    suspend fun getHomePersonalizedRandom(
        @Body requestDto: ForeignExhibitListRequestDto
    ): BaseResponseDto<List<ExhibitResponseDto>>

    @POST("${HOME_PATH}/personalized/random")
    suspend fun getHomePersonalizedRandom(
        @Body requestDto: DomesticExhibitListRequestDto
    ): BaseResponseDto<List<ExhibitResponseDto>>

    @POST("${HOME_PATH}/schedule")
    suspend fun getHomeSchedule(
        @Body requestDto: ExhibitListRequestDto
    ): BaseResponseDto<List<ExhibitResponseDto>>

    @POST("${HOME_PATH}/genre/random")
    suspend fun getHomeGenreRandom(
        @Body requestDto: ExhibitListRequestDto
    ): BaseResponseDto<List<ExhibitResponseDto>>
}
