package com.arttrip.android.data.remote.api

import com.arttrip.android.data.remote.api.ApiConstants.MAP_PATH
import com.arttrip.android.data.remote.model.map.ExhibitMarkerResDto
import retrofit2.http.GET
import retrofit2.http.Header

interface MapApi {
    @GET("${MAP_PATH}/exhibits/markers")
    suspend fun getExhibitMarkers(
        @Header("If-None-Match") etag: String,
    ): ExhibitMarkerResDto
}
