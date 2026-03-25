package com.arttrip.android.data.repository

import com.arttrip.android.data.remote.datasource.SearchHistoryDataSource
import com.arttrip.android.data.remote.mapper.base.toAppError
import com.arttrip.android.data.remote.mapper.searchhistory.toDomain
import com.arttrip.android.domain.model.network.ApiResult
import com.arttrip.android.domain.model.recentsearch.RecentSearch
import com.arttrip.android.domain.repository.SearchHistoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException

class SearchHistoryRepositoryImpl
    @Inject
    constructor(
        private val dataSource: SearchHistoryDataSource,
    ) : SearchHistoryRepository {
        override fun getRecentSearchList(): Flow<ApiResult<List<RecentSearch>>> =
            flow {
                emit(ApiResult.Loading)

                try {
                    val result = dataSource.getSearchHistory().toDomain()
                    emit(ApiResult.Success(result))
                } catch (e: Exception) {
                    if (e is CancellationException) throw e
                    emit(ApiResult.Error(e.toAppError()))
                }
            }

        override fun deleteRecentSearch(id: Int): Flow<ApiResult<Unit>> =
            flow {
                emit(ApiResult.Loading)

                try {
                    dataSource.deleteSearchHistory(id = id)
                    emit(ApiResult.Success(Unit))
                } catch (e: Exception) {
                    if (e is CancellationException) throw e
                    emit(ApiResult.Error(e.toAppError()))
                }
            }

        override fun deleteAllRecentSearch(): Flow<ApiResult<Unit>> =
            flow {
                emit(ApiResult.Loading)

                try {
                    dataSource.deleteAllSearchHistory()
                    emit(ApiResult.Success(Unit))
                } catch (e: Exception) {
                    if (e is CancellationException) throw e
                    emit(ApiResult.Error(e.toAppError()))
                }
            }
    }
