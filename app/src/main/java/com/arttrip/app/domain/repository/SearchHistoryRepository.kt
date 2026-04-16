package com.arttrip.app.domain.repository

import com.arttrip.app.domain.model.network.ApiResult
import com.arttrip.app.domain.model.recentsearch.RecentSearch
import kotlinx.coroutines.flow.Flow

interface SearchHistoryRepository {
    fun getRecentSearchList(): Flow<ApiResult<List<RecentSearch>>>

    fun deleteRecentSearch(id: Int): Flow<ApiResult<Unit>>

    fun deleteAllRecentSearch(): Flow<ApiResult<Unit>>
}
