package com.arttrip.android.domain.usecase.auth

import com.arttrip.android.domain.model.auth.LoginProvider
import com.arttrip.android.domain.model.auth.LoginResult
import com.arttrip.android.domain.model.network.ApiResult
import com.arttrip.android.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SocialLoginUseCase
    @Inject
    constructor(
        private val authRepository: AuthRepository,
    ) {
        operator fun invoke(
            provider: LoginProvider,
            idToken: String,
        ): Flow<ApiResult<LoginResult>> = authRepository.socialLogin(provider, idToken)
    }
