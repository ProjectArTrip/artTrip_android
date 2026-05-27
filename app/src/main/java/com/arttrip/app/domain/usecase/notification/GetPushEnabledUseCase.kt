package com.arttrip.app.domain.usecase.notification

import com.arttrip.app.domain.model.network.ApiResult
import com.arttrip.app.domain.repository.NotificationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPushEnabledUseCase
    @Inject
    constructor(
        private val notificationRepository: NotificationRepository,
    ) {
        operator fun invoke(): Flow<ApiResult<Boolean>> = notificationRepository.getPushEnabled()
    }
