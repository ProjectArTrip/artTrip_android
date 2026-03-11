package com.arttrip.android.data.remote.model.review

data class UserReviewResDto(
    val reviewId: Int,
    val reviewTitle: String,
    val content: String,
    val photoUrls: List<String>?,
    val posterUrl: String,
    val visitDate: String,
    val createdAt: String,
)
