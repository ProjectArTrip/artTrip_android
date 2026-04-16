package com.arttrip.app.domain.usecase.search

import com.arttrip.app.domain.model.network.ApiResult
import com.arttrip.app.domain.repository.SearchHistoryRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DeleteAllRecentSearchUseCase
    @Inject
    constructor(
        private val searchHistoryRepository: SearchHistoryRepository,
    ) {
        operator fun invoke(): Flow<ApiResult<Unit>> = searchHistoryRepository.deleteAllRecentSearch()
    }
