package com.arttrip.app.domain.model.notice

import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

data class Notice(
    val userNoticeId: Int,
    val title: String,
    val body: String,
    val createdAt: Instant,
)

private val noticeDateFormatter: DateTimeFormatter =
    DateTimeFormatter.ISO_LOCAL_DATE // yyyy-MM-dd

fun Instant.toNoticeDateText(zoneId: ZoneId = ZoneId.systemDefault()): String =
    this
        .atZone(zoneId)
        .toLocalDate()
        .format(noticeDateFormatter)
