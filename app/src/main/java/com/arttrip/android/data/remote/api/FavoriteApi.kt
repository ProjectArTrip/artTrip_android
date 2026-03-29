package com.arttrip.android.data.remote.api

import com.arttrip.android.data.remote.api.ApiConstants.FAVORITE_PATH
import com.arttrip.android.data.remote.model.favorite.FavoritePageResDto
import com.arttrip.android.data.remote.model.favorite.FavoriteResDto
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface FavoriteApi {
    @POST("${FAVORITE_PATH}/{exhibitId}")
    suspend fun postFavorite(
        @Path("exhibitId") exhibitId: Int,
    ): Unit

    @DELETE("${FAVORITE_PATH}/{exhibitId}")
    suspend fun deleteFavorite(
        @Path("exhibitId") exhibitId: Int,
    ): Unit

    @GET(FAVORITE_PATH)
    suspend fun getFavorites(
        @Query("sortOption") sortType: String,
        @Query("regions") regions: List<String>? = null,
        @Query("countries") countries: List<String>? = null,
        @Query("cursor") cursor: Int? = null,
        @Query("size") size: Int,
    ): FavoritePageResDto<FavoriteResDto>
}
