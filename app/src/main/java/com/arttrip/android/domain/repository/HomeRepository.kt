package com.arttrip.android.domain.repository

import com.arttrip.android.domain.model.network.ApiResult
import kotlinx.coroutines.flow.Flow

interface HomeRepository {
    fun getCountryList(): Flow<ApiResult<List<String>>>
}
