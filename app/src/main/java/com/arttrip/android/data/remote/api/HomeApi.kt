package com.arttrip.android.data.remote.api

import com.arttrip.android.data.remote.api.ApiConstants.HOME_PATH
import com.arttrip.android.data.remote.model.home.ExhibitResponseDto
import com.arttrip.android.data.remote.model.home.ExhibitListRequestDto
import com.arttrip.android.data.remote.model.network.BaseResponseDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface HomeApi {
    @POST("${HOME_PATH}/recommend/today")
    suspend fun getHomeRecommendToday(
        @Body requestDto: ExhibitListRequestDto
    ): BaseResponseDto<List<ExhibitResponseDto>>

    @POST("${HOME_PATH}/personalized/random")
    suspend fun getHomePersonalizedRandom(
        @Body requestDto: ExhibitListRequestDto
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
