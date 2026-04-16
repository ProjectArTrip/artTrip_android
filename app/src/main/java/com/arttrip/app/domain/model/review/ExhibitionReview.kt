package com.arttrip.app.domain.model.review

data class ExhibitionReview(
    val id: Int,
    val reviewer: String,
    val visitDate: String,
    val content: String,
    val photoUrls: List<String>,
)
