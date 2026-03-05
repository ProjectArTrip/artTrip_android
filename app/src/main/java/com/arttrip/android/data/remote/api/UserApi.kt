package com.arttrip.android.data.remote.api

import com.arttrip.android.data.remote.api.ApiConstants.USER_PATH
import com.arttrip.android.data.remote.model.user.UserInfoResDto
import com.arttrip.android.data.remote.model.user.UserNicknameReqDto
import com.arttrip.android.data.remote.model.user.UserRecentExhibitsResDto
import okhttp3.MultipartBody
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.Part
import retrofit2.http.Query

interface UserApi {
    @GET(USER_PATH)
    suspend fun getUserInfo(
        @Query("w") w: Int,
        @Query("h") h: Int,
        @Query("f") f: String,
    ): UserInfoResDto

    @PATCH(USER_PATH)
    suspend fun patchUserNickname(
        @Body body: UserNicknameReqDto,
    ): Unit

    @DELETE("${USER_PATH}/image")
    suspend fun deleteProfileImage(): Unit

    @Multipart
    @PATCH("${USER_PATH}/image")
    suspend fun patchProfileImage(
        @Part image: MultipartBody.Part,
    ): Unit

    @GET("${USER_PATH}/recent-exhibits")
    suspend fun getUserRecentExhibits(
        @Query("w") w: Int,
        @Query("h") h: Int,
        @Query("f") f: String,
    ): UserRecentExhibitsResDto
}
