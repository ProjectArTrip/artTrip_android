package com.arttrip.app.domain.model.notice

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

data class Notice(
    val userNoticeId: Int,
    val title: String,
    val content: String,
    val createdAt: LocalDateTime,
)

private val noticeDateFormatter: DateTimeFormatter =
    DateTimeFormatter.ISO_LOCAL_DATE // yyyy-MM-dd

fun LocalDateTime.toNoticeDateText(): String = this.toLocalDate().format(noticeDateFormatter)
