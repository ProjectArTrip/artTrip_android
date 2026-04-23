package com.arttrip.app.domain.usecase.auth

import com.arttrip.app.domain.model.auth.LoginResult
import com.arttrip.app.domain.model.auth.SocialLoginCredential
import com.arttrip.app.domain.model.network.ApiResult
import com.arttrip.app.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SocialLoginUseCase
    @Inject
    constructor(
        private val authRepository: AuthRepository,
    ) {
        operator fun invoke(credential: SocialLoginCredential): Flow<ApiResult<LoginResult>> = authRepository.socialLogin(credential)
    }
