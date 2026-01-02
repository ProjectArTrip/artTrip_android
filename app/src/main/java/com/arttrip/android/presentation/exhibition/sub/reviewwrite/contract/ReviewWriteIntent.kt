package com.arttrip.android.presentation.exhibition.sub.reviewwrite.contract

import android.net.Uri
import com.arttrip.android.presentation.exhibition.sub.reviewwrite.model.ReviewWritePrefill

sealed interface ReviewWriteIntent {
    data class Initialize(
        val prefill: ReviewWritePrefill,
    ) : ReviewWriteIntent

    data object BackClicked : ReviewWriteIntent

    // 방문일
    data object VisitDateClicked : ReviewWriteIntent

    data object CalendarClicked : ReviewWriteIntent // TODO 제거

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
