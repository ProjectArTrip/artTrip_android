package com.arttrip.app.domain.usecase.search

import com.arttrip.app.domain.model.network.ApiResult
import com.arttrip.app.domain.model.usertaste.Taste
import com.arttrip.app.domain.repository.UserTasteRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetRecommendKeywordUseCase
    @Inject
    constructor(
        private val userTasteRepository: UserTasteRepository,
    ) {
        operator fun invoke(): Flow<ApiResult<List<Taste>>> = userTasteRepository.getRecommendKeywords()
    }
