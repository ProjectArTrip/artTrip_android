package com.arttrip.android.data.remote.api

import com.arttrip.android.core.model.enums.exhibition.SortType
import com.arttrip.android.data.remote.api.ApiConstants.EXHIBIT_PATH
import com.arttrip.android.data.remote.model.exhibit.ExhibitDetailResponseDto
import com.arttrip.android.data.remote.model.home.ExhibitListPageResDto
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
    ): ExhibitDetailResponseDto

    @GET(EXHIBIT_PATH)
    suspend fun getExhibits(
        @Query("cursor") cursor: Int? = null,
        @Query("size") size: Int = 20,
        @Query("query") query: String?,
        @Query("startDate") startDate: String?,
        @Query("endDate") endDate: String?,
        @Query("isDomestic") isDomestic: Boolean?,
        @Query("country") country: String?,
        @Query("region") region: String?,
        @Query("genres") genres: List<String>?,
        @Query("styles") styles: List<String>?,
        @Query("sortType") sortType: SortType?,
    ): ExhibitListPageResDto
}
