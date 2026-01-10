package com.arttrip.android.domain.model.review

data class Review(
    val id: Int,
    val writer: String,
    val visitDate: String,
    val content: String,
    val photoUrls: List<String>,
)
