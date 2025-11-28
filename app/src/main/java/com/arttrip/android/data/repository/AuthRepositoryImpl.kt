package com.arttrip.android.data.repository

import com.arttrip.android.data.remote.api.AuthApi
import com.arttrip.android.data.remote.model.auth.LoginRequest
import com.arttrip.android.domain.model.auth.LoginResult
import com.arttrip.android.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authApi: AuthApi,
) : AuthRepository {

    override suspend fun loginWithKakao(idToken: String): LoginResult {
        val response = authApi.loginWithKakao(
            body = LoginRequest(idToken = idToken)
        )
        // 응답 매핑 처리
        return LoginResult(
            accessToken = response.accessToken,
            refreshToken = response.refreshToken,
            isFirstLogin = response.isFirstLogin,
        )
    }
}
