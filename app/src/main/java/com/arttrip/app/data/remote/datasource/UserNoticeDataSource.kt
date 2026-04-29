package com.arttrip.app.data.remote.datasource

import com.arttrip.app.data.remote.api.UserNoticeApi
import com.arttrip.app.data.remote.model.usernotice.NotificationPageResDto
import javax.inject.Inject

class UserNoticeDataSource
    @Inject
    constructor(
        private val api: UserNoticeApi,
    ) {
        suspend fun getNotices(
            cursor: Int?,
            size: Int,
        ): NotificationPageResDto =
            api.getNotifications(
                cursor = cursor,
                size = size,
            )
    }
