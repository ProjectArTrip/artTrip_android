package com.arttrip.android.data.remote.api

import com.arttrip.android.data.remote.api.ApiConstants.EXHIBIT_PATH
import com.arttrip.android.data.remote.model.home.ExhibitDetailResponseDto
import com.arttrip.android.data.remote.model.network.BaseResponseDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ExhibitApi {
    @GET("$EXHIBIT_PATH/{id}")
    suspend fun getExhibitDetail(
        @Path("id") id: Int,
        @Query("w") w: Int,
        @Query("h") h: Int,
        @Query("f") f: String,
    ): BaseResponseDto<ExhibitDetailResponseDto>
}
