package com.arttrip.app.data.remote.mapper.usernotice

import com.arttrip.app.data.remote.model.usernotice.NotificationDto
import com.arttrip.app.domain.model.notice.Notice
import java.time.LocalDateTime

fun NotificationDto.toDomain(): Notice =
    Notice(
        userNoticeId = userNoticeId,
        referenceId = referenceId,
        title = title,
        content = body,
        createdAt = LocalDateTime.parse(createdAt),
    )
