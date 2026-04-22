package com.arttrip.app.data.local.auth

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.arttrip.app.domain.model.auth.AuthTokens
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TokenManager
    @Inject
    constructor(
        private val dataStore: DataStore<Preferences>,
    ) {
        companion object {
            private val KEY_ACCESS = stringPreferencesKey("access_token")
            private val KEY_REFRESH = stringPreferencesKey("refresh_token")
            private const val TAG = "TokenManager"
        }

        private val scope = CoroutineScope(Dispatchers.IO)

        @Volatile
        private var cachedTokens: AuthTokens? = null

        init {
            loadFromDataStore()
        }

        fun getAccessToken(): String? {
            Log.d(TAG, "getAccessToken() from CACHE, length=${cachedTokens?.accessToken?.length}")
            return cachedTokens?.accessToken
        }

        fun getRefreshToken(): String? {
            Log.d(TAG, "getRefreshToken() from CACHE, length=${cachedTokens?.refreshToken?.length}")
            return cachedTokens?.refreshToken
        }

        // 생성 시점에 DataStore → cache 로드 (이후 호출은 cache에서 반환)
        private fun loadFromDataStore() {
            val prefs = runBlocking { dataStore.data.first() }
            val access = prefs[KEY_ACCESS]
            val refresh = prefs[KEY_REFRESH]

            Log.d(TAG, "loadFromDataStore(), accessIsNull=${access == null}, refreshIsNull=${refresh == null}")

            if (access != null && refresh != null) {
                cachedTokens = AuthTokens(access, refresh)
            }
        }

        fun saveTokens(tokens: AuthTokens) {
            cachedTokens = tokens

            Log.d(TAG, "saveTokens() called, accessLength=${tokens.accessToken.length}, refreshLength=${tokens.refreshToken.length}")

            scope.launch {
                dataStore.edit { prefs ->
                    prefs[KEY_ACCESS] = tokens.accessToken
                    prefs[KEY_REFRESH] = tokens.refreshToken
                }
            }
        }

        fun clear() {
            Log.d(TAG, "clear() called, tokens will be removed")

            cachedTokens = null

            scope.launch {
                dataStore.edit { it.clear() }
            }
        }

        fun hasTokens(): Boolean {
            val access = getAccessToken()
            val refresh = getRefreshToken()
            val result = !access.isNullOrBlank() && !refresh.isNullOrBlank()

            Log.d(
                TAG,
                "hasTokens() -> $result (accessIsNullOrBlank=${access.isNullOrBlank()}, refreshIsNullOrBlank=${refresh.isNullOrBlank()})",
            )

            return result
        }
    }
