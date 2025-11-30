package com.arttrip.android.data.remote.api

import com.arttrip.android.data.remote.model.network.BaseResponseDto
import retrofit2.http.GET

interface HomeApi {
    @GET(ApiConstants.HOME_INTER_PATH)
    suspend fun getCountryList(): BaseResponseDto<List<String>>
}