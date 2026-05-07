package com.arttrip.app.data.remote.model.usernotice

data class NotificationDto(
    val userNoticeId: Int,
    val action: String,
    val referenceId: Int,
    val title: String,
    val body: String,
    val isRead: Boolean,
    val createdAt: String,
)
