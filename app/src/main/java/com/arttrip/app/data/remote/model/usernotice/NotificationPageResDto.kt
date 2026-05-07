package com.arttrip.app.data.remote.model.usernotice

data class NotificationPageResDto(
    val notifications: List<NotificationDto> = emptyList(),
    val nextCursor: Int?,
    val hasNext: Boolean,
)
