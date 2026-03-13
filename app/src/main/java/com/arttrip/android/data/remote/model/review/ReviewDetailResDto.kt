package com.arttrip.android.data.remote.model.review

data class ReviewDetailResDto(
    val reviewId: Int,
    val date: String, // "2025-12-16"
    val content: String,
    val photoUrls: List<String>?,
    val images: List<ReviewImageResDto>? = null,
)

data class ReviewImageResDto(
    val reviewImageId: Int,
    val imageUrl: String,
)
