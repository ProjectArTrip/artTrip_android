package com.arttrip.app.data.remote.api

import com.arttrip.app.data.remote.api.ApiConstants.USER_NOTICE_PATH
import com.arttrip.app.data.remote.model.usernotice.NotificationPageResDto
import retrofit2.http.GET
import retrofit2.http.Query

interface UserNoticeApi {
    @GET(USER_NOTICE_PATH)
    suspend fun getNotifications(
        @Query("cursor") cursor: Int? = null,
        @Query("size") size: Int = 20,
    ): NotificationPageResDto
}
