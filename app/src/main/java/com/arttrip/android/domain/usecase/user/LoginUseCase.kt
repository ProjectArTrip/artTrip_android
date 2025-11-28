package com.arttrip.android.domain.usecase.user

import com.arttrip.android.domain.model.auth.LoginResult
import com.arttrip.android.domain.model.network.ApiResult
import com.arttrip.android.domain.model.user.UserInfoModel
import com.arttrip.android.domain.repository.AuthRepository
import com.arttrip.android.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class KakaoLoginUseCase(
    @Inject
    constructor(
    private val authRepository: AuthRepository,
) {
    suspend operator fun invoke(idToken: String): LoginResult = authRepository.loginWithKakao(idToken)
}

