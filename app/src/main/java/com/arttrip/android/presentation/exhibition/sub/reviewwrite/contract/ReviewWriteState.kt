package com.arttrip.android.presentation.exhibition.sub.reviewwrite.contract

import android.net.Uri
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter

private const val MAX_REVIEW_PHOTO_COUNT = 4
private const val MAX_REVIEW_TEXT_LENGTH = 500
private val VISIT_DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy.MM.dd")

data class ReviewWriteState(
    val title: String = "",
    val hallName: String = "",
    val posterUrl: String? = null,
    val visitDate: LocalDate? = null,
    val isVisitDateSheetVisible: Boolean = false,
    val calendarMonth: YearMonth = YearMonth.now(),
    val reviewText: String = "",
    val photoUris: List<Uri> = emptyList(),
    val isSubmitting: Boolean = false,
) {
    val maxPhotoCount: Int get() = MAX_REVIEW_PHOTO_COUNT
    val maxTextLength: Int get() = MAX_REVIEW_TEXT_LENGTH
    val canAddPhoto: Boolean get() = photoUris.size < maxPhotoCount
    val canSubmit: Boolean get() =
        !isSubmitting &&
            reviewText.isNotBlank() &&
            visitDate != null
    val visitDateText: String? get() = visitDate?.format(VISIT_DATE_FORMATTER)
}
