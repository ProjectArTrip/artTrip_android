package com.arttrip.android.domain.usecase.intro

import com.arttrip.android.domain.model.network.ApiResult
import com.arttrip.android.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SaveIntroKeywordsUseCase
    @Inject
    constructor(
        private val authRepository: AuthRepository,
    ) {
        operator fun invoke(
            genreIds: Set<Int>,
            styleIds: Set<Int>,
        ): Flow<ApiResult<Unit>> =
            authRepository.saveUserKeywords(
                (genreIds + styleIds).toList(),
            )
    }
