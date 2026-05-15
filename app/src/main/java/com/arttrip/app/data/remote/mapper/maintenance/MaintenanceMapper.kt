package com.arttrip.app.data.remote.mapper.maintenance

import com.arttrip.app.data.remote.model.maintenance.MaintenanceStatusResDto
import com.arttrip.app.domain.model.maintenance.Maintenance
import com.arttrip.app.domain.model.maintenance.MaintenanceState
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

private val inputFormatter = DateTimeFormatter.ISO_ZONED_DATE_TIME
private val dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일(E) HH:mm", Locale.KOREAN)
private val timeFormatter = DateTimeFormatter.ofPattern("HH:mm", Locale.KOREAN)

private fun String?.toFormattedDateTime(): String? =
    runCatching { ZonedDateTime.parse(this, inputFormatter).format(dateTimeFormatter) }.getOrNull()

private fun String?.toFormattedTime(): String? = runCatching { ZonedDateTime.parse(this, inputFormatter).format(timeFormatter) }.getOrNull()

fun MaintenanceStatusResDto.toDomain(): Maintenance =
    Maintenance(
        state = MaintenanceState.entries.find { it.name == state } ?: MaintenanceState.NORMAL,
        startAt = startAt.toFormattedDateTime(),
        endAt = endAt.toFormattedTime(),
    )
