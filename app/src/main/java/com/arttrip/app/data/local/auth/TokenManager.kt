package com.arttrip.app.data.local.auth

import android.content.SharedPreferences
import android.util.Log
import androidx.core.content.edit
import com.arttrip.app.domain.model.auth.AuthTokens
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TokenManager
    @Inject
    constructor(
        private val prefs: SharedPreferences,
    ) {
        companion object {
            private const val KEY_ACCESS = "access_token"
            private const val KEY_REFRESH = "refresh_token"
            private const val TAG = "TokenManager"
        }

        @Volatile
        private var cachedTokens: AuthTokens? = null

        fun getAccessToken(): String? {
            // 캐시에 있으면 캐시에서 꺼냄
            cachedTokens?.accessToken?.let { access ->
                Log.d(TAG, "getAccessToken() from CACHE, length=${access.length}")
                return access
            }

            // 캐시에 없으면 SharedPreferences에서 로드
            val access = prefs.getString(KEY_ACCESS, null)
            val refresh = prefs.getString(KEY_REFRESH, null)

            Log.d(
                TAG,
                "getAccessToken() from PREFS, " +
                    "accessIsNull=${access == null}, refreshIsNull=${refresh == null}",
            )

            if (access != null && refresh != null) {
                cachedTokens = AuthTokens(access, refresh)
            }

            return access
        }

        fun getRefreshToken(): String? {
            // 캐시에 있으면 캐시에서 꺼냄
            cachedTokens?.refreshToken?.let { refresh ->
                Log.d(TAG, "getRefreshToken() from CACHE, length=${refresh.length}")
                return refresh
            }

            // 캐시에 없으면 SharedPreferences에서 로드
            val refresh = prefs.getString(KEY_REFRESH, null)
            val access = prefs.getString(KEY_ACCESS, null)

            Log.d(
                TAG,
                "getRefreshToken() from PREFS, " +
                    "refreshIsNull=${refresh == null}, accessIsNull=${access == null}",
            )

            if (access != null && refresh != null) {
                cachedTokens = AuthTokens(access, refresh)
            }

            return refresh
        }

        fun saveTokens(tokens: AuthTokens) {
            cachedTokens = tokens

            Log.d(
                TAG,
                "saveTokens() called, accessLength=${tokens.accessToken.length}, " +
                    "refreshLength=${tokens.refreshToken.length}",
            )

            prefs.edit {
                putString(KEY_ACCESS, tokens.accessToken)
                putString(KEY_REFRESH, tokens.refreshToken)
            }
        }

        fun clear() {
            Log.d(TAG, "clear() called, tokens will be removed")

            cachedTokens = null
            prefs.edit {
                remove(KEY_ACCESS)
                remove(KEY_REFRESH)
            }
        }

        fun hasTokens(): Boolean {
            val access = getAccessToken()
            val refresh = getRefreshToken()
            val result = !access.isNullOrBlank() && !refresh.isNullOrBlank()

            Log.d(
                TAG,
                "hasTokens() -> $result (accessIsNullOrBlank=${access.isNullOrBlank()}, " +
                    "refreshIsNullOrBlank=${refresh.isNullOrBlank()})",
            )

            return result
        }
    }
