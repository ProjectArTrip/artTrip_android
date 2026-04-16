package com.arttrip.app.data.remote.api

import com.arttrip.app.data.remote.api.ApiConstants.MAP_PATH
import com.arttrip.app.data.remote.model.home.ExhibitListPageResDto
import com.arttrip.app.data.remote.model.map.ExhibitMarkerResDto
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface MapApi {
    @GET("${MAP_PATH}/exhibits/markers")
    suspend fun getExhibitMarkers(
        @Header("If-None-Match") etag: String,
    ): ExhibitMarkerResDto

    @GET("${MAP_PATH}/cluster")
    suspend fun getClusterExhibits(
        @Query("ids") ids: List<Int>,
        @Query("cursor") cursor: Int? = null,
        @Query("size") size: Int = 20,
    ): ExhibitListPageResDto
}
