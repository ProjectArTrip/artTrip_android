package com.arttrip.android.presentation.reviewwrite.contract

import android.net.Uri
import com.arttrip.android.presentation.reviewwrite.model.ReviewWriteMode
import java.time.LocalDate
import java.time.YearMonth

sealed interface ReviewWriteIntent {
    data class Initialize(
        val mode: ReviewWriteMode,
    ) : ReviewWriteIntent

    data object BackClicked : ReviewWriteIntent

    // 방문일
    data object VisitDateClicked : ReviewWriteIntent

    data object VisitDateSheetDismissed : ReviewWriteIntent

    data class VisitDateSelected(
        val date: LocalDate,
    ) : ReviewWriteIntent

    data class CalendarMonthChanged(
        val month: YearMonth,
    ) : ReviewWriteIntent

    // 사진
    data class PhotoPickerResult(
        val uris: List<Uri>,
    ) : ReviewWriteIntent

    data object AddPhotoClicked : ReviewWriteIntent

    data class RemovePhotoClicked(
        val index: Int,
    ) : ReviewWriteIntent

    // 리뷰 텍스트
    data class ReviewTextChanged(
        val text: String,
    ) : ReviewWriteIntent

    // 제출
    data object SubmitClicked : ReviewWriteIntent
}
