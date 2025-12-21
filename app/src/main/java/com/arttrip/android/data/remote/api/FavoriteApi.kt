package com.arttrip.android.data.remote.api

import com.arttrip.android.data.remote.api.ApiConstants.FAVORITE_PATH
import com.arttrip.android.data.remote.model.favorite.AddFavoriteResDto
import com.arttrip.android.data.remote.model.favorite.FavoriteCheckResDto
import com.arttrip.android.data.remote.model.network.BaseResponseDto
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface FavoriteApi {
    @POST("${FAVORITE_PATH}/{exhibitId}")
    suspend fun postFavorite(
        @Path("exhibitId") exhibitId: Int,
    ): BaseResponseDto<AddFavoriteResDto>

    @DELETE("${FAVORITE_PATH}/{exhibitId}")
    suspend fun deleteFavorite(
        @Path("exhibitId") exhibitId: Int,
    ): BaseResponseDto<Unit>

    @GET("${FAVORITE_PATH}/check/{exhibitId}")
    suspend fun getIsFavorite(
        @Path("exhibitId") exhibitId: Int,
    ): BaseResponseDto<FavoriteCheckResDto>
}
