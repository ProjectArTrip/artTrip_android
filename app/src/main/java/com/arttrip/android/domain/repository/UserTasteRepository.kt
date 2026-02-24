package com.arttrip.android.domain.repository

import com.arttrip.android.domain.model.network.ApiResult
import com.arttrip.android.domain.model.usertaste.TasteGroup
import kotlinx.coroutines.flow.Flow

interface UserTasteRepository {
    fun getAllTasteGroups(): Flow<ApiResult<TasteGroup>>

    fun getUserTasteGroups(): Flow<ApiResult<TasteGroup>>

    fun saveUserTaste(tasteIds: List<Int>): Flow<ApiResult<Unit>>
}
