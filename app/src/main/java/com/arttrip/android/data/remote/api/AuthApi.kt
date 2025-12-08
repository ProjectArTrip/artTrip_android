package com.arttrip.android.data.remote.api

import com.arttrip.android.data.remote.api.ApiConstants.AUTH_PATH
import com.arttrip.android.data.remote.model.auth.KeywordsResponseDto
import com.arttrip.android.data.remote.model.auth.LoginRequestDto
import com.arttrip.android.data.remote.model.auth.LoginResponseDto
import com.arttrip.android.data.remote.model.auth.RefreshRequestDto
import com.arttrip.android.data.remote.model.auth.RefreshResponseDto
import com.arttrip.android.data.remote.model.auth.UserKeywordsRequestDto
import com.arttrip.android.data.remote.model.network.BaseResponseDto
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AuthApi {
    @POST("${AUTH_PATH}/social")
    suspend fun postLogin(
        @Body body: LoginRequestDto,
    ): BaseResponseDto<LoginResponseDto>

    @POST("${AUTH_PATH}/app/reissue")
    fun refreshTokens(
        @Body body: RefreshRequestDto,
    ): Call<BaseResponseDto<RefreshResponseDto>>

    @GET("${AUTH_PATH}/allkeywords")
    suspend fun getAllKeywords(): BaseResponseDto<List<KeywordsResponseDto>>

    @POST("${AUTH_PATH}/keywords")
    suspend fun postUserKeywords(
        @Body body: UserKeywordsRequestDto,
    ): BaseResponseDto<String>
}
