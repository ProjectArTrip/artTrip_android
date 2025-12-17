package com.arttrip.android.data.remote.model.review

data class ReviewDetailPageResponseDto(
    val reviews: List<ReviewDetailDto> = emptyList(),
    val nextCursor: Int?,
    val hasNext: Boolean,
)

data class ReviewDetailDto(
    val reviewId: Int,
    val reviewTitle: String,
    val visitDate: String, // "2025-12-16"
    val content: String,
    val createdAt: String, // "2025-12-16T07:34:21.517Z"
    val thumbnailUrl: List<String>? = null, // TODO 확인필요
)
