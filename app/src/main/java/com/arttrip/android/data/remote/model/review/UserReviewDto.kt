package com.arttrip.android.data.remote.model.review

data class UserReviewDto(
    val reviewId: Int,
    val reviewTitle: String,
    val content: String,
    val visitDate: String,
    val createdAt: String,
)
