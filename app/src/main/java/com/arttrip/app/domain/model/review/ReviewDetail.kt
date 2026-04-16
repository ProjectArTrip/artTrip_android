package com.arttrip.app.domain.model.review

data class ReviewDetail(
    val reviewId: Int,
    val visitDate: String,
    val content: String,
    val images: List<ReviewImage>,
)

data class ReviewImage(
    val imageId: Int,
    val imageUrl: String,
)
