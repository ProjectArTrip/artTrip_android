package com.arttrip.android.data.remote.api

import com.arttrip.android.data.remote.api.ApiConstants.AUTH_PATH
import com.arttrip.android.data.remote.model.auth.KeywordsResDto
import com.arttrip.android.data.remote.model.auth.LoginReqDto
import com.arttrip.android.data.remote.model.auth.LoginResDto
import com.arttrip.android.data.remote.model.auth.RefreshReqDto
import com.arttrip.android.data.remote.model.auth.RefreshResDto
import com.arttrip.android.data.remote.model.auth.UserKeywordsReqDto
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AuthApi {
    @POST("${AUTH_PATH}/social")
    suspend fun postLogin(
        @Body body: LoginReqDto,
    ): LoginResDto

    @POST("${AUTH_PATH}/app/reissue")
    fun refreshTokens(
        @Body body: RefreshReqDto,
    ): Call<RefreshResDto>

    @GET("${AUTH_PATH}/allkeywords")
    suspend fun getAllKeywords(): List<KeywordsResDto>

    @POST("${AUTH_PATH}/keywords")
    suspend fun postUserKeywords(
        @Body body: UserKeywordsReqDto,
    ): String
}
