package com.arttrip.app.data.remote.api

import com.arttrip.app.data.remote.api.ApiConstants.CURATION_PATH
import com.arttrip.app.data.remote.model.curation.CurationExhibitListResDto
import com.arttrip.app.data.remote.model.curation.CurationResponseDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CurationApi {
    @GET(CURATION_PATH)
    suspend fun getCurations(
        @Query("domestic") domestic: Boolean,
        @Query("country") country: String?,
    ): CurationResponseDto

    @GET("$CURATION_PATH/{curationId}")
    suspend fun getCurationExhibits(
        @Path("curationId") curationId: Long,
        @Query("cursor") cursor: Int?,
        @Query("size") size: Int,
    ): CurationExhibitListResDto
}
