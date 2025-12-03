package com.arttrip.android.domain.usecase.login

import com.arttrip.android.domain.model.auth.LoginModel
import com.arttrip.android.domain.model.auth.LoginProvider
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
        ): Flow<ApiResult<LoginModel>> = authRepository.socialLogin(provider, idToken)
    }
