package com.arttrip.app.data.remote.api

import com.arttrip.app.data.remote.api.ApiConstants.AUTH_PATH
import com.arttrip.app.data.remote.model.auth.DeleteUserAccountReqDto
import com.arttrip.app.data.remote.model.auth.LoginReqDto
import com.arttrip.app.data.remote.model.auth.LoginResDto
import com.arttrip.app.data.remote.model.auth.RefreshReqDto
import com.arttrip.app.data.remote.model.auth.RefreshResDto
import retrofit2.Call
import retrofit2.http.Body
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

    @POST("${AUTH_PATH}/withdraw")
    suspend fun withdrawAccount(
        @Body body: DeleteUserAccountReqDto,
    )
}
