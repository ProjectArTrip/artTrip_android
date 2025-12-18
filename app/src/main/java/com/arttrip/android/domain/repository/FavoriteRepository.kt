package com.arttrip.android.domain.repository

import com.arttrip.android.domain.model.favorite.FavoriteCheckModel
import com.arttrip.android.domain.model.favorite.FavoriteResult
import com.arttrip.android.domain.model.network.ApiResult
import kotlinx.coroutines.flow.Flow

interface FavoriteRepository {
    fun addFavorite(exhibitId: Int): Flow<ApiResult<FavoriteResult>>

    fun removeFavorite(exhibitId: Int): Flow<ApiResult<Unit>>

    fun checkFavorite(exhibitId: Int): Flow<ApiResult<FavoriteCheckModel>>
}
