package com.arttrip.android.data.remote.interceptor

import com.arttrip.android.core.di.RefreshAuthApi
import com.arttrip.android.data.local.auth.SessionManager
import com.arttrip.android.data.local.auth.TokenManager
import com.arttrip.android.data.remote.api.ApiConstants.AUTH_PATH
import com.arttrip.android.data.remote.api.AuthApi
import com.arttrip.android.data.remote.model.auth.RefreshRequestDto
import com.arttrip.android.data.remote.model.auth.RefreshResponseDto
import com.arttrip.android.domain.model.auth.AuthTokens
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TokenAuthenticator
    @Inject
    constructor(
        private val tokenManager: TokenManager,
        private val sessionManager: SessionManager,
        @param:RefreshAuthApi private val authApi: AuthApi,
    ) : Authenticator {
        override fun authenticate(
            route: Route?,
            response: Response,
        ): Request? {
            if (!shouldAttemptRefresh(response)) return null

            if (!isAccessTokenExpired(response)) return null

            val newTokens = tryRefreshTokens() ?: return null

            return rebuildRequest(response.request, newTokens)
        }

        private fun shouldAttemptRefresh(response: Response): Boolean {
            // 무한 루프 방지
            if (responseDepth(response) >= 2) return false

            val path = response.request.url.encodedPath
            if (isAuthApi(path)) return false

            if (tokenManager.getRefreshToken().isNullOrBlank()) return false

            return true
        }

        private fun isAuthApi(path: String): Boolean =
            path.endsWith("${AUTH_PATH}/social") ||
                path.endsWith("${AUTH_PATH}/app/reissue")

        private fun responseDepth(response: Response): Int {
            var depth = 1
            var prev = response.priorResponse
            while (prev != null) {
                depth++
                prev = prev.priorResponse
            }
            return depth
        }

        private fun isAccessTokenExpired(response: Response): Boolean {
//            val errorBody = response.peekBody(Long.MAX_VALUE).string()
//            if (errorBody.isBlank()) return false
//
//            return parseTokenExpired(errorBody)
            // TODO: 서버에서 명확한 토큰 만료 코드를 주기 전까지는 401로만 판단
            return response.code == 401
        }

//        private fun parseTokenExpired(body: String): Boolean =
//            try {
//                val code = JSONObject(body).optString("code")
//                code in setOf("COMMON401", "TOKEN_EXPIRED", "REFRESH_TOKEN_EXPIRED")
//            } catch (_: Exception) {
//                false
//            }

        private fun tryRefreshTokens(): AuthTokens? {
            val refreshToken = tokenManager.getRefreshToken() ?: return null

            val response =
                try {
                    authApi.refreshTokens(RefreshRequestDto(refreshToken)).execute()
                } catch (_: Exception) {
                    return null
                }

            // ---(임시) HTTP 레벨에서 refresh 401 = Refresh Token 만료 ---
            if (response.code() == 401) {
                expireSession()
                return null
            }

            // --- HTTP 오류 ---
            if (!response.isSuccessful) {
                expireTokens()
                return null
            }

            val body =
                response.body()
                    ?: return expireTokens()

            // --- 비즈니스 레벨 실패 ---
            if (!body.isSuccess || body.result == null) {
                return handleRefreshBusinessFailure(body.code)
            }

            return saveNewTokens(body.result)
        }

        private fun expireSession() {
            tokenManager.clear()
            sessionManager.logout()
        }

        private fun expireTokens(): AuthTokens? {
            tokenManager.clear()
            return null
        }

        private fun handleRefreshBusinessFailure(code: String): AuthTokens? {
            if (code == "COMMON401") {
                expireSession()
            } else {
                tokenManager.clear()
            }
            return null
        }

        private fun saveNewTokens(result: RefreshResponseDto): AuthTokens {
            val newTokens =
                AuthTokens(
                    accessToken = result.accessToken,
                    refreshToken = result.refreshToken,
                )
            tokenManager.saveTokens(newTokens)
            return newTokens
        }

        private fun rebuildRequest(
            original: Request,
            newTokens: AuthTokens,
        ): Request = original.newBuilder().build() // Header 추가는 상위 Interceptor에서 처리 중이라 그대로 유지
    }
