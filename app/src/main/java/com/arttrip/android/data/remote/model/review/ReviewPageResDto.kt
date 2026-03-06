package com.arttrip.android.data.remote.model.review

data class ReviewPageResDto(
    val reviews: List<ReviewDto> = emptyList(),
    val nextCursor: Int?,
    val hasNext: Boolean,
    val reviewTotalCount: Int,
)

data class ReviewDto(
    val reviewId: Int,
    val visitDate: String, // "2025-12-16"
    val content: String,
    val thumbnailUrl: String?,
    val nickname: String?,
)
