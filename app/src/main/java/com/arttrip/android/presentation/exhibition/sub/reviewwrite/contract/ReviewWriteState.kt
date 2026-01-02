package com.arttrip.android.presentation.exhibition.sub.reviewwrite.contract

import android.net.Uri

private const val MAX_REVIEW_PHOTO_COUNT = 4
private const val MAX_REVIEW_TEXT_LENGTH = 500

data class ReviewWriteState(
    val title: String = "",
    val hallName: String = "",
    val posterUrl: String? = null,
    val visitDateText: String? = null, // "2026.01.02" 같은 표시용
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
            visitDateText != null
}
