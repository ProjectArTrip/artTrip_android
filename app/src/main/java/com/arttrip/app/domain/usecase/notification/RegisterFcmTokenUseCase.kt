package com.arttrip.app.domain.usecase.notification

import com.arttrip.app.domain.model.network.ApiResult
import com.arttrip.app.domain.repository.NotificationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RegisterFcmTokenUseCase
    @Inject
    constructor(
        private val notificationRepository: NotificationRepository,
    ) {
        operator fun invoke(fcmToken: String): Flow<ApiResult<Unit>> = notificationRepository.registerUserFcmToken(fcmToken = fcmToken)
    }
