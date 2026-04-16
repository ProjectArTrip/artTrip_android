package com.arttrip.app.domain.usecase.search

import com.arttrip.app.domain.model.network.ApiResult
import com.arttrip.app.domain.repository.SearchHistoryRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DeleteRecentSearchUseCase
    @Inject
    constructor(
        private val searchHistoryRepository: SearchHistoryRepository,
    ) {
        operator fun invoke(id: Int): Flow<ApiResult<Unit>> = searchHistoryRepository.deleteRecentSearch(id = id)
    }
