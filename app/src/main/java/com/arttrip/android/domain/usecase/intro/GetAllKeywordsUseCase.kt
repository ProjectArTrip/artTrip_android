package com.arttrip.android.domain.usecase.intro

import com.arttrip.android.domain.model.auth.KeywordGroups
import com.arttrip.android.domain.model.network.ApiResult
import com.arttrip.android.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllKeywordsUseCase
    @Inject
    constructor(
        private val authRepository: AuthRepository,
    ) {
        operator fun invoke(): Flow<ApiResult<KeywordGroups>> = authRepository.getAllKeywords()
    }
