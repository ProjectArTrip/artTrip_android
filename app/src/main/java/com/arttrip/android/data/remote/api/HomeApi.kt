package com.arttrip.android.data.remote.api

import com.arttrip.android.data.remote.model.home.ExhibitDto
import com.arttrip.android.data.remote.model.network.BaseResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface HomeApi {
    @GET(ApiConstants.HOME_INTER_PATH)
    suspend fun getCountryList(): BaseResponseDto<List<String>>

    @GET(ApiConstants.HOME_RECOMMEND_TODAY_PATH)
    suspend fun getHomeRecommendToday(
        @Query("isDomestic") isDomestic: Boolean
    ): BaseResponseDto<List<ExhibitDto>>
}
