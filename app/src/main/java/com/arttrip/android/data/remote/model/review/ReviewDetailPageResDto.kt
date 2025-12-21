package com.arttrip.android.data.remote.model.review

data class ReviewDetailPageResDto(
    val reviews: List<ReviewDetailDto> = emptyList(),
    val nextCursor: Int?,
    val hasNext: Boolean,
    val reviewTotalCount: Int,
)

data class ReviewDetailDto(
    val reviewId: Int,
    val visitDate: String, // "2025-12-16"
    val content: String,
    val thumbnailUrl: String?,
    val nickname: String,
)
