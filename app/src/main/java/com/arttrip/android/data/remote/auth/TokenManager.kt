package com.arttrip.android.data.remote.auth

import android.content.SharedPreferences
import com.arttrip.android.domain.model.auth.AuthTokens
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
        }

        // in-memory 캐시
        @Volatile
        private var cachedTokens: AuthTokens? = null

        fun getAccessToken(): String? {
            return cachedTokens?.accessToken
                ?: prefs.getString(KEY_ACCESS, null)?.also { access ->
                    val refresh = prefs.getString(KEY_REFRESH, null) ?: return@also
                    cachedTokens = AuthTokens(access, refresh)
                }
        }

        fun getRefreshToken(): String? {
            return cachedTokens?.refreshToken
                ?: prefs.getString(KEY_REFRESH, null)?.also { refresh ->
                    val access = prefs.getString(KEY_ACCESS, null) ?: return@also
                    cachedTokens = AuthTokens(access, refresh)
                }
        }

        fun saveTokens(tokens: AuthTokens) {
            cachedTokens = tokens
            prefs
                .edit()
                .putString(KEY_ACCESS, tokens.accessToken)
                .putString(KEY_REFRESH, tokens.refreshToken)
                .apply()
        }

        fun clear() {
            cachedTokens = null
            prefs
                .edit()
                .remove(KEY_ACCESS)
                .remove(KEY_REFRESH)
                .apply()
        }

        fun hasTokens(): Boolean {
            val access = getAccessToken()
            val refresh = getRefreshToken()
            return !access.isNullOrBlank() && !refresh.isNullOrBlank()
        }
    }
