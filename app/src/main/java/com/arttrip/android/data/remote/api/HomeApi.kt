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
import retrofit2.http.Query

interface HomeApi {
    @POST("${HOME_PATH}/recommend/today")
    suspend fun getHomeRecommendToday(
        @Query("w") width: Int,
        @Query("h") height: Int,
        @Query("f") format: String,
        @Body requestDto: ForeignRecommendExhibitListRequestDto,
    ): BaseResponseDto<List<ForeignExhibitResponseDto>>

    @POST("${HOME_PATH}/recommend/today")
    suspend fun getHomeRecommendToday(
        @Query("w") width: Int,
        @Query("h") height: Int,
        @Query("f") format: String,
        @Body requestDto: DomesticRecommendExhibitListRequestDto,
    ): BaseResponseDto<List<DomesticExhibitResponseDto>>

    @POST("${HOME_PATH}/personalized/random")
    suspend fun getHomePersonalizedRandom(
        @Query("w") width: Int,
        @Query("h") height: Int,
        @Query("f") format: String,
        @Body requestDto: ForeignPersonalizedExhibitListRequestDto,
    ): BaseResponseDto<List<ForeignExhibitResponseDto>>

    @POST("${HOME_PATH}/personalized/random")
    suspend fun getHomePersonalizedRandom(
        @Query("w") width: Int,
        @Query("h") height: Int,
        @Query("f") format: String,
        @Body requestDto: DomesticPersonalizedExhibitListRequestDto,
    ): BaseResponseDto<List<DomesticExhibitResponseDto>>

    @POST("${HOME_PATH}/schedule")
    suspend fun getHomeSchedule(
        @Query("w") width: Int,
        @Query("h") height: Int,
        @Query("f") format: String,
        @Body requestDto: ForeignScheduleExhibitListRequestDto,
    ): BaseResponseDto<List<ForeignExhibitResponseDto>>

    @POST("${HOME_PATH}/schedule")
    suspend fun getHomeSchedule(
        @Query("w") width: Int,
        @Query("h") height: Int,
        @Query("f") format: String,
        @Body requestDto: DomesticScheduleExhibitListRequestDto,
    ): BaseResponseDto<List<DomesticExhibitResponseDto>>

    @POST("${HOME_PATH}/genre/random")
    suspend fun getHomeGenreRandom(
        @Query("w") width: Int,
        @Query("h") height: Int,
        @Query("f") format: String,
        @Body requestDto: ForeignGenreExhibitListRequestDto,
    ): BaseResponseDto<List<ForeignExhibitResponseDto>>

    @POST("${HOME_PATH}/genre/random")
    suspend fun getHomeGenreRandom(
        @Query("w") width: Int,
        @Query("h") height: Int,
        @Query("f") format: String,
        @Body requestDto: DomesticGenreExhibitListRequestDto,
    ): BaseResponseDto<List<DomesticExhibitResponseDto>>
}
