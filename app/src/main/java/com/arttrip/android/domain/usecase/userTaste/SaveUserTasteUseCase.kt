package com.arttrip.android.domain.usecase.userTaste

import com.arttrip.android.domain.model.network.ApiResult
import com.arttrip.android.domain.repository.KeywordRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SaveUserTasteUseCase
    @Inject
    constructor(
        private val keywordRepository: KeywordRepository,
    ) {
        operator fun invoke(
            genreIds: Set<Int>,
            styleIds: Set<Int>,
        ): Flow<ApiResult<Unit>> =
            keywordRepository.saveUserTaste(
                (genreIds + styleIds).toList(),
            )
    }
