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
    @GET("${HOME_PATH}/overseas")
    suspend fun getCountryList(): BaseResponseDto<List<String>>

    @POST("${HOME_PATH}/recommend/today")
    suspend fun getHomeRecommendToday(
        @Body requestDto: ExhibitListRequestDto,
    ): BaseResponseDto<List<ExhibitResponseDto>>

    @GET("${HOME_PATH}/personalized/random")
    suspend fun getHomePersonalized(
        @Query("isDomestic") isDomestic: Boolean,
    ): BaseResponseDto<List<ExhibitResponseDto>>

    @GET("${HOME_PATH}/schedule")
    suspend fun getHomeSchedule(
        @Query("isDomestic") isDomestic: Boolean,
        @Query("date") date: String,
    ): BaseResponseDto<List<ExhibitResponseDto>>
}
