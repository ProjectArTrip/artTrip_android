package com.arttrip.app.data.remote.mapper.usernotice

import com.arttrip.app.core.model.enums.notification.Action
import com.arttrip.app.data.remote.model.usernotice.NotificationDto
import com.arttrip.app.domain.model.notice.Notice
import com.arttrip.app.domain.model.notification.Notification
import java.time.LocalDateTime

fun NotificationDto.toDomain(): Notice =
    Notice(
        userNoticeId = userNoticeId,
        referenceId = referenceId,
        title = title,
        content = body,
        createdAt = LocalDateTime.parse(createdAt),
    )

fun NotificationDto.toNotification(): Notification =
    Notification(
        userNoticeId = userNoticeId,
        action = Action.entries.find { it.name == action } ?: Action.MOVE_NOTICE_DETAIL,
        referenceId = referenceId,
        title = title,
        body = body,
        isRead = isRead,
        createdAt = LocalDateTime.parse(createdAt),
    )
