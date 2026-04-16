package com.arttrip.app.data.remote.model.review

data class ExhibitReviewResDto(
    val reviewId: Int,
    val visitDate: String, // "2025-12-16"
    val content: String,
    val photoUrls: List<String>?,
    val reviewer: String?,
)
