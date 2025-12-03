package com.arttrip.android.data.remote.interceptor

import com.arttrip.android.core.di.RefreshAuthApi
import com.arttrip.android.data.local.auth.SessionManager
import com.arttrip.android.data.local.auth.TokenManager
import com.arttrip.android.data.remote.api.ApiConstants.AUTH_PATH
import com.arttrip.android.data.remote.api.AuthApi
import com.arttrip.android.data.remote.model.auth.RefreshRequestDto
import com.arttrip.android.data.remote.model.auth.RefreshResponseDto
import com.arttrip.android.data.remote.model.network.BaseResponseDto
import com.arttrip.android.domain.model.auth.AuthTokens
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import org.json.JSONObject
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
            if (shouldSkipAuthenticate(response)) {
                return null
            }

            val errorBody = response.peekBody(Long.MAX_VALUE).string()
            if (!isTokenExpired(errorBody)) {
                return null
            }

            val newTokens = tryRefreshTokens() ?: return null

            return buildRetriedRequest(response.request, newTokens)
        }

        private fun shouldSkipAuthenticate(response: Response): Boolean {
            // 무한 루프 방지
            if (responseCount(response) >= 2) {
                return true
            }

            // 로그인/소셜/리프레시 자체에서 401이면 리프레시 시도 X
            val path = response.request.url.encodedPath
            if (isAuthRelatedPath(path)) {
                return true
            }

            // 저장된 refreshToken이 없으면 시도 불가
            if (tokenManager.getRefreshToken().isNullOrBlank()) {
                return true
            }

            return false
        }

        private fun isAuthRelatedPath(path: String): Boolean {
            return path.endsWith("${AUTH_PATH}/social") ||
                path.endsWith("${AUTH_PATH}/app/reissue")
            // TODO: /auth/login 등 추가 필요하면 여기서만 관리
        }

        private fun responseCount(response: Response): Int {
            var count = 1
            var current: Response? = response.priorResponse
            while (current != null) {
                count++
                current = current.priorResponse
            }
            return count
        }

        // 원래 응답이 "토큰 만료"인지 판단
        private fun isTokenExpired(errorBody: String): Boolean {
            if (errorBody.isBlank()) return false

            return try {
                val json = JSONObject(errorBody)
                val code = json.optString("code")

                when (code) {
                    "COMMON401", // TODO: ACCESS_TOKEN_EXPIRED 같은 코드만 true
                    "TOKEN_EXPIRED",
                    "REFRESH_TOKEN_EXPIRED",
                    -> true

                    else -> false
                }
            } catch (e: Exception) {
                // 에러 형태가 예상과 다르면 "토큰 만료"로 간주하지 않음
                false
            }
        }

        private fun tryRefreshTokens(): AuthTokens? {
            val refreshToken = tokenManager.getRefreshToken() ?: return null

            val call =
                authApi.refreshTokens(
                    RefreshRequestDto(refreshToken = refreshToken),
                )

            val response =
                try {
                    call.execute()
                } catch (e: Exception) {
                    // 네트워크/서버 문제 → 여기서는 바로 로그아웃까지는 하지 않고 null 반환
                    return null
                }

            if (!response.isSuccessful) {
                // HTTP 레벨에서 실패
                tokenManager.clear()
                return null
            }

            val body: BaseResponseDto<RefreshResponseDto> =
                response.body() ?: run {
                    tokenManager.clear()
                    return null
                }

            // 비즈니스 레벨 실패 처리
            if (!body.isSuccess || body.result == null) {
                when (body.code) {
                    // TODO: REFRESH_TOKEN_EXPIRED 코드에서만 logout
                    "COMMON401" -> {
                        tokenManager.clear()
                        sessionManager.logout()
                    }
                    else -> {
                        // 다른 에러 코드들에 대한 정책이 정해지면 여기에서 분기
                        tokenManager.clear() // 지금은 일단 전부 날려버리기
                    }
                }
                return null
            }

            val result = body.result

            val newTokens =
                AuthTokens(
                    accessToken = result.accessToken,
                    refreshToken = result.refreshToken,
                )
            tokenManager.saveTokens(newTokens)

            return newTokens
        }

        private fun buildRetriedRequest(
            original: Request,
            newTokens: AuthTokens,
        ): Request = original.newBuilder().build()
    }
