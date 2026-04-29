package com.arttrip.app.data.remote.mapper.usernotice

import com.arttrip.app.data.remote.model.usernotice.NotificationDto
import com.arttrip.app.domain.model.notice.Notice
import java.time.Instant

fun NotificationDto.toDomain(): Notice =
    Notice(
        userNoticeId = userNoticeId,
        title = title,
        body = body,
        createdAt = Instant.parse(createdAt),
    )
