package com.arttrip.android.data.remote.api

import com.arttrip.android.data.remote.model.auth.LoginRequest
import com.arttrip.android.data.remote.model.auth.LoginResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {

    @POST("auth/login/kakao")
    suspend fun loginWithKakao(
        @Body body: LoginRequest
    ): LoginResponse
}