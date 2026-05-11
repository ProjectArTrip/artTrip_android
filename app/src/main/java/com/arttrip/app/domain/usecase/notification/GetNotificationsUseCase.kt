package com.arttrip.app.domain.usecase.notification

import androidx.paging.PagingData
import com.arttrip.app.domain.model.notification.Notification
import com.arttrip.app.domain.repository.NotificationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetNotificationsUseCase
    @Inject
    constructor(
        private val notificationRepository: NotificationRepository,
    ) {
        operator fun invoke(): Flow<PagingData<Notification>> = notificationRepository.getNotifications()
    }
