package com.arttrip.app.data.remote.datasource

import com.arttrip.app.data.remote.api.SearchHistoryApi
import javax.inject.Inject

class SearchHistoryDataSource
    @Inject
    constructor(
        private val api: SearchHistoryApi,
    ) {
        suspend fun getSearchHistory() = api.getSearchHistory()

        suspend fun deleteSearchHistory(id: Int) = api.deleteSearchHistory(id = id)

        suspend fun deleteAllSearchHistory() = api.deleteAllSearchHistory()
    }
