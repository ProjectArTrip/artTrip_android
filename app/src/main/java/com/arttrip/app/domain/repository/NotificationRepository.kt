package com.arttrip.app.domain.repository

import androidx.paging.PagingData
import com.arttrip.app.domain.model.network.ApiResult
import com.arttrip.app.domain.model.notification.Notification
import kotlinx.coroutines.flow.Flow

interface NotificationRepository {
    fun registerUserFcmToken(fcmToken: String): Flow<ApiResult<Unit>>

    fun getNotifications(
        pageSize: Int = 20,
        initialLoadSize: Int = 20,
    ): Flow<PagingData<Notification>>

    fun readNotification(userNoticeId: Int): Flow<ApiResult<Unit>>
}
