package com.arttrip.app.domain.repository

import com.arttrip.app.domain.model.network.ApiResult
import com.arttrip.app.domain.model.usertaste.Taste
import com.arttrip.app.domain.model.usertaste.TasteGroup
import kotlinx.coroutines.flow.Flow

interface UserTasteRepository {
    fun getAllTasteGroups(): Flow<ApiResult<TasteGroup>>

    fun getUserTasteGroups(): Flow<ApiResult<TasteGroup>>

    fun saveUserTaste(tastes: List<String>): Flow<ApiResult<Unit>>

    fun getRecommendKeywords(): Flow<ApiResult<List<Taste>>>
}
