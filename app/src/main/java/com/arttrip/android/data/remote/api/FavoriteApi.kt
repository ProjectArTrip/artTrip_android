package com.arttrip.android.data.remote.api

import com.arttrip.android.data.remote.api.ApiConstants.FAVORITE_PATH
import com.arttrip.android.data.remote.model.favorite.AddFavoriteResDto
import retrofit2.http.DELETE
import retrofit2.http.POST
import retrofit2.http.Path

interface FavoriteApi {
    @POST("${FAVORITE_PATH}/{exhibitId}")
    suspend fun postFavorite(
        @Path("exhibitId") exhibitId: Int,
    ): AddFavoriteResDto

    @DELETE("${FAVORITE_PATH}/{exhibitId}")
    suspend fun deleteFavorite(
        @Path("exhibitId") exhibitId: Int,
    ): Unit
}
