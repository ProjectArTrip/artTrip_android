package com.arttrip.android.data.remote.interceptor

import android.util.Log
import com.arttrip.android.core.di.RefreshAuthApi
import com.arttrip.android.data.local.auth.SessionManager
import com.arttrip.android.data.local.auth.TokenManager
import com.arttrip.android.data.remote.api.ApiConstants.AUTH_PATH
import com.arttrip.android.data.remote.api.AuthApi
import com.arttrip.android.data.remote.model.auth.RefreshReqDto
import com.arttrip.android.data.remote.model.auth.RefreshResDto
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
        companion object {
            private const val TAG = "TokenAuth"
            private const val HEADER_AUTHORIZATION = "Authorization"
            private const val TOKEN_PREFIX = "Bearer "
            private const val MAX_RETRY_COUNT = 2
        }

        override fun authenticate(
            route: Route?,
            response: Response,
        ): Request? {
            if (!shouldAttemptRefresh(response)) return null
            if (response.code != 401) return null

            Log.d(
                TAG,
                "401 from ${response.request.method} ${response.request.url} retry=${response.retryCount}",
            )

            synchronized(this) {
                val currentAccessToken = tokenManager.getAccessToken()
                val failedRequestToken = response.request.getRawAccessToken()

                if (currentAccessToken != null && currentAccessToken != failedRequestToken) {
                    Log.d(TAG, "⏩ 이미 다른 스레드에서 토큰 갱신됨. API 호출 Skip.")
                    return rebuildRequest(
                        response.request,
                        currentAccessToken,
                    )
                }

                val newTokens = tryRefreshTokens() ?: return null // 실패 시 null 반환 (로그아웃 등 처리됨)

                return rebuildRequest(response.request, newTokens.accessToken)
            }
        }

        /**
         * 리프레시 요청을 시도해야 하는지 판단하는 로직
         */
        private fun shouldAttemptRefresh(response: Response): Boolean {
            if (response.retryCount >= MAX_RETRY_COUNT) return false // 무한 루프 방지
            if (isAuthApi(response.request.url.encodedPath)) return false // 로그인/재발급 API는 제외
            if (tokenManager.getRefreshToken().isNullOrBlank()) return false // 리프레시 토큰 없으면 불가
            return true
        }

        private fun isAuthApi(path: String): Boolean = path.endsWith("$AUTH_PATH/social") || path.endsWith("$AUTH_PATH/app/reissue")

        /**
         * 실제 서버에 토큰 갱신을 요청하는 로직
         */
        private fun tryRefreshTokens(): AuthTokens? {
            val refreshToken = tokenManager.getRefreshToken() ?: return null
            Log.d(TAG, "🔄 Refresh Token 요청 시작")

            val response =
                try {
                    authApi.refreshTokens(RefreshReqDto(refreshToken)).execute()
                } catch (e: Exception) {
                    Log.e(TAG, "❗ Refresh 요청 실패 (네트워크 오류): ${e.message}")
                    return null // 네트워크 오류 시 그냥 종료 (다음 요청 때 다시 시도)
                }

            if (response.code() == 401) {
                Log.e(TAG, "❌ Refresh Token 만료 → 로그아웃 처리")
                performLogout()
                return null
            }

            if (!response.isSuccessful) {
                Log.e(TAG, "❗ Refresh 요청 HTTP 실패 (${response.code()}) → 토큰 제거")
                clearTokens()
                return null
            }

            val body = response.body()

            if (body == null || !body.isSuccess || body.result == null) {
                Log.w(TAG, "⚠️ Refresh 비즈니스 실패 (code=${body?.code})")
                handleBusinessFailure(body?.code)
                return null
            }

            Log.d(TAG, "✅ Refresh 성공 → 새 토큰 저장 완료")
            return saveNewTokens(body.result)
        }

        private fun rebuildRequest(
            original: Request,
            newAccessToken: String,
        ): Request {
            Log.d(TAG, "rebuildRequest: 새 AccessToken으로 재요청 빌드")
            return original
                .newBuilder()
                .header(HEADER_AUTHORIZATION, "$TOKEN_PREFIX$newAccessToken")
                .build()
        }

        private fun saveNewTokens(result: RefreshResDto): AuthTokens {
            val newTokens =
                AuthTokens(
                    accessToken = result.accessToken,
                    refreshToken = result.refreshToken,
                )
            tokenManager.saveTokens(newTokens)
            return newTokens
        }

        // --- Cleanup Logic ---

        private fun handleBusinessFailure(code: String?) {
            Log.e(TAG, "⚠️ Refresh 비즈니스 로직 실패(code=$code) → 강제 로그아웃 진행")
            performLogout()
        }

        private fun performLogout() {
            tokenManager.clear() // 토큰 삭제
            sessionManager.logout() // 로그인 상태 해제 & 로그인 화면으로 이동
        }

        private fun clearTokens() {
            tokenManager.clear()
        }

        private fun Request.getRawAccessToken(): String? {
            val header = this.header(HEADER_AUTHORIZATION) ?: return null
            return if (header.startsWith(TOKEN_PREFIX, ignoreCase = true)) {
                header.substring(TOKEN_PREFIX.length)
            } else {
                header
            }
        }

        private val Response.retryCount: Int
            get() {
                var count = 0
                var prev = this.priorResponse
                while (prev != null) {
                    count++
                    prev = prev.priorResponse
                }
                return count
            }
    }
