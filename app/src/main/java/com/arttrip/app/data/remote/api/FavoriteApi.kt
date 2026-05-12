package com.arttrip.app.data.remote.api

import com.arttrip.app.data.remote.api.ApiConstants.FAVORITE_PATH
import com.arttrip.app.data.remote.model.favorite.FavoritePageResDto
import com.arttrip.app.data.remote.model.favorite.FavoriteResDto
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
        @Query("sortType") sortType: String,
        @Query("region") region: String? = null,
        @Query("country") country: String? = null,
        @Query("cursor") cursor: Int? = null,
        @Query("size") size: Int,
    ): FavoritePageResDto<FavoriteResDto>
}
