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
        }

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

            val expired = response.code == 401
            if (expired) {
                Log.d(TAG, "🔥 Access Token 만료 감지 → Refresh 시도")
            }
            return expired
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
            Log.d(TAG, "🔄 Refresh Token 요청 시작")
            val response =
                try {
                    authApi.refreshTokens(RefreshReqDto(refreshToken)).execute()
                } catch (e: Exception) {
                    Log.e(TAG, "❗ Refresh 요청 실패 (네트워크 오류): ${e.message}")
                    return null
                }

            // ---(임시) HTTP 레벨에서 refresh 401 = Refresh Token 만료 ---
            if (response.code() == 401) {
                Log.e(TAG, "❌ Refresh Token 만료 → 로그아웃 처리됨")
                expireSession()
                return null
            }

            // --- HTTP 오류 ---
            if (!response.isSuccessful) {
                Log.e(TAG, "❗ Refresh 요청 HTTP 실패 (${response.code()}) → 토큰 제거")
                expireTokens()
                return null
            }

            val body =
                response.body()
                    ?: run {
                        Log.e(TAG, "❗ Refresh 응답 body null → 토큰 제거")
                        return expireTokens()
                    }

            // --- 비즈니스 레벨 실패 ---
            if (!body.isSuccess || body.result == null) {
                Log.w(TAG, "⚠️ Refresh 비즈니스 실패(code=${body.code})")
                return handleRefreshBusinessFailure(body.code)
            }

            Log.d("TokenAuth", "✅ Refresh 성공 → 새 토큰 저장 완료")
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

        private fun saveNewTokens(result: RefreshResDto): AuthTokens {
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
        ): Request {
            val newRequest =
                original
                    .newBuilder()
                    .header("Authorization", "Bearer ${newTokens.accessToken}")
                    .build()

            Log.d(TAG, "rebuildRequest: 새 AccessToken으로 요청 재전송 - ${original.url.encodedPath}")

            return newRequest
        }
    }
