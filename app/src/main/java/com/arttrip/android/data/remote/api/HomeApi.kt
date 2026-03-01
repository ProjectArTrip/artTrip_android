package com.arttrip.android.data.remote.api

import com.arttrip.android.data.remote.api.ApiConstants.HOME_PATH
import com.arttrip.android.data.remote.model.home.DomesticExhibitListResponseDto
import com.arttrip.android.data.remote.model.home.ForeignExhibitListResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface HomeApi {
    @GET("${HOME_PATH}/today")
    suspend fun getForeignHomeRecommendToday(
        @Query("isDomestic") isDomestic: Boolean,
        @Query("country") country: String,
    ): ForeignExhibitListResponseDto

    @GET("${HOME_PATH}/today")
    suspend fun getDomesticHomeRecommendToday(
        @Query("isDomestic") isDomestic: Boolean,
        @Query("region") region: String,
    ): DomesticExhibitListResponseDto

    @GET("${HOME_PATH}/personalized")
    suspend fun getForeignHomePersonalizedRandom(
        @Query("isDomestic") isDomestic: Boolean,
        @Query("country") country: String,
    ): ForeignExhibitListResponseDto

    @GET("${HOME_PATH}/personalized")
    suspend fun getDomesticHomePersonalizedRandom(
        @Query("isDomestic") isDomestic: Boolean,
        @Query("region") region: String,
    ): DomesticExhibitListResponseDto

    @GET("${HOME_PATH}/schedule")
    suspend fun getForeignHomeSchedule(
        @Query("isDomestic") isDomestic: Boolean,
        @Query("date") date: String,
        @Query("country") country: String,
    ): ForeignExhibitListResponseDto

    @GET("${HOME_PATH}/schedule")
    suspend fun getDomesticHomeSchedule(
        @Query("isDomestic") isDomestic: Boolean,
        @Query("date") date: String,
        @Query("region") region: String,
    ): DomesticExhibitListResponseDto

    @GET("${HOME_PATH}/genres")
    suspend fun getForeignHomeGenreRandom(
        @Query("isDomestic") isDomestic: Boolean,
        @Query("singleGenre") singleGenre: String,
        @Query("country") country: String,
    ): ForeignExhibitListResponseDto

    @GET("${HOME_PATH}/genres")
    suspend fun getDomesticHomeGenreRandom(
        @Query("isDomestic") isDomestic: Boolean,
        @Query("singleGenre") singleGenre: String,
        @Query("region") region: String,
    ): DomesticExhibitListResponseDto
}
