package com.arttrip.app.domain.model.notification

import com.arttrip.app.core.model.enums.notification.Action
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

data class Notification(
    val userNoticeId: Int,
    val action: Action,
    val referenceId: Int,
    val title: String,
    val body: String,
    val isRead: Boolean,
    val createdAt: LocalDateTime,
)

fun LocalDateTime.toRelativeDateText(): String {
    val now = LocalDateTime.now()
    val minutes = ChronoUnit.MINUTES.between(this, now)
    return when {
        minutes < 1 -> "방금 전"
        minutes < 60 -> "${minutes}분 전"
        minutes < 60 * 24 -> "${minutes / 60}시간 전"
        else -> "${minutes / (60 * 24)}일 전"
    }
}
