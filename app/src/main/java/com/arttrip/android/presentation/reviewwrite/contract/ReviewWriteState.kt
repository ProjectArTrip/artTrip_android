package com.arttrip.android.presentation.reviewwrite.contract

import android.net.Uri
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter

const val MAX_REVIEW_PHOTO_COUNT = 4
const val MAX_REVIEW_TEXT_LENGTH = 500
const val MIN_REVIEW_TEXT_LENGTH = 20
private val VISIT_DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy.MM.dd")

data class ReviewWriteState(
    val appTopBarTitle: String = "리뷰 작성",
    val buttonText: String = "등록하기",
    val title: String = "",
    val hallName: String = "",
    val posterUrl: String? = null,
    val visitDate: LocalDate? = null,
    val isVisitDateSheetVisible: Boolean = false,
    val calendarMonth: YearMonth = YearMonth.now(),
    val reviewText: String = "",
    val photoUris: List<Uri> = emptyList(),
    val isSubmitting: Boolean = false,
    val showReviewLengthError: Boolean = false,
) {
    val canAddPhoto: Boolean get() = photoUris.size < MAX_REVIEW_PHOTO_COUNT
    val canSubmit: Boolean
        get() =
            !isSubmitting &&
                reviewText.isNotBlank() &&
                visitDate != null

    val visitDateText: String?
        get() = visitDate?.format(VISIT_DATE_FORMATTER)
}
