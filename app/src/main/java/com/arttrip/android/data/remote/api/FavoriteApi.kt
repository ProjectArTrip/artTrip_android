package com.arttrip.android.data.remote.api

import com.arttrip.android.data.remote.api.ApiConstants.FAVORITE_PATH
import com.arttrip.android.data.remote.model.favorite.AddFavoriteResponseDto
import com.arttrip.android.data.remote.model.favorite.FavoriteCheckResponseDto
import com.arttrip.android.data.remote.model.network.BaseResponseDto
import retrofit2.Call
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface FavoriteApi {
    @POST("${FAVORITE_PATH}/{exhibitId}")
    fun postFavorite(
        @Path("exhibitId") exhibitId: Int,
    ): Call<BaseResponseDto<AddFavoriteResponseDto>>

    @DELETE("${FAVORITE_PATH}/{exhibitId}")
    fun deleteFavorite(
        @Path("exhibitId") exhibitId: Int,
    ): Call<BaseResponseDto<Unit>>

    @GET("${FAVORITE_PATH}/check/{exhibitId}")
    suspend fun getIsFavorite(
        @Path("exhibitId") exhibitId: Int,
    ): BaseResponseDto<FavoriteCheckResponseDto>
}
