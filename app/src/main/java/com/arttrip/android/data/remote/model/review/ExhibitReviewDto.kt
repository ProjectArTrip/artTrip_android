package com.arttrip.android.data.remote.model.review

data class ExhibitReviewDto(
    val reviewId: Int,
    val visitDate: String, // "2025-12-16"
    val content: String,
    val thumbnailUrl: String?,
    val nickname: String?,
)
