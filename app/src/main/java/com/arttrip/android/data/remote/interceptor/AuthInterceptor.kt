package com.arttrip.android.data.remote.interceptor

import com.arttrip.android.data.local.auth.TokenManager
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthInterceptor
    @Inject
    constructor(
        private val tokenManager: TokenManager,
    ) : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            val original = chain.request()

            val accessToken = tokenManager.getAccessToken()
            if (accessToken.isNullOrBlank()) {
                return chain.proceed(original)
            }

            val newRequest =
                original
                    .newBuilder()
                    .addHeader("Authorization", "Bearer $accessToken")
                    .build()

            return chain.proceed(newRequest)
        }
    }
