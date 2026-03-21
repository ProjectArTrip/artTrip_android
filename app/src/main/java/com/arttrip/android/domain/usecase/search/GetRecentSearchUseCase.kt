package com.arttrip.android.domain.usecase.search

import com.arttrip.android.domain.model.network.ApiResult
import com.arttrip.android.domain.model.recentsearch.RecentSearch
import com.arttrip.android.domain.repository.SearchHistoryRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetRecentSearchUseCase
    @Inject
    constructor(
        private val searchHistoryRepository: SearchHistoryRepository,
    ) {
        operator fun invoke(): Flow<ApiResult<List<RecentSearch>>> =
            searchHistoryRepository.getRecentSearchList()
    }