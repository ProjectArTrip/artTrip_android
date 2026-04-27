package com.arttrip.app.domain.repository

import com.arttrip.app.domain.model.network.ApiResult
import kotlinx.coroutines.flow.Flow

interface NotificationRepository {
    fun registerUserFcmToken(fcmToken: String): Flow<ApiResult<Unit>>
}
