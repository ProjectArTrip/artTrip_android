package com.arttrip.app.data.remote.api

import com.arttrip.app.data.remote.api.ApiConstants.USER_PATH
import com.arttrip.app.data.remote.model.user.UserFcmTokenReqDto
import com.arttrip.app.data.remote.model.user.UserNicknameReqDto
import com.arttrip.app.data.remote.model.user.UserPushEnabledReqDto
import com.arttrip.app.data.remote.model.user.UserRecentExhibitsResDto
import com.arttrip.app.data.remote.model.user.UserResDto
import okhttp3.MultipartBody
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

interface UserApi {
    @GET(USER_PATH)
    suspend fun getUserInfo(
        @Query("w") w: Int,
        @Query("h") h: Int,
        @Query("f") f: String,
    ): UserResDto

    @PATCH(USER_PATH)
    suspend fun patchUserNickname(
        @Body body: UserNicknameReqDto,
    ): UserResDto

    @DELETE("${USER_PATH}/image")
    suspend fun deleteProfileImage(): Unit

    @Multipart
    @PATCH("${USER_PATH}/image")
    suspend fun patchProfileImage(
        @Part image: MultipartBody.Part,
    ): UserResDto

    @GET("${USER_PATH}/recent-exhibits")
    suspend fun getUserRecentExhibits(
        @Query("w") w: Int,
        @Query("h") h: Int,
        @Query("f") f: String,
    ): UserRecentExhibitsResDto

    @POST("${USER_PATH}/fcm-token")
    suspend fun postFcmToken(
        @Body body: UserFcmTokenReqDto,
    ): Unit

    @PATCH("${USER_PATH}/push-enabled")
    suspend fun patchPushEnabled(
        @Body body: UserPushEnabledReqDto,
    ): Unit
}
