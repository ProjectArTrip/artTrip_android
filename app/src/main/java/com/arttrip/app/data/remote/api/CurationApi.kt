package com.arttrip.app.data.remote.api

import com.arttrip.app.data.remote.api.ApiConstants.CURATION_PATH
import com.arttrip.app.data.remote.model.curation.CurationResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface CurationApi {
    @GET(CURATION_PATH)
    suspend fun getCurations(
        @Query("domestic") domestic: Boolean,
        @Query("country") country: String?,
    ): CurationResponse
}