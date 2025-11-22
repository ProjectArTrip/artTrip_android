package com.arttrip.android.data.remote.api

import com.arttrip.android.data.remote.api.ApiConstants.USER_PATH
import com.arttrip.android.data.remote.model.network.BaseResponseDto
import com.arttrip.android.data.remote.model.user.UserInfoDto
import retrofit2.http.GET
import retrofit2.http.Path

interface UserApi {
    @GET("${USER_PATH}/{id}")
    suspend fun getUserInfo(
        @Path("id") id: Int,
    ): BaseResponseDto<UserInfoDto>
}
