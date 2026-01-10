package com.arttrip.android.domain.model.review

data class MyReview(
    val id: Int,
    val exhibitionId: Int,
    val exhibitionTitle: String,
    val visitedDate: String,
    val content: String,
    val thumbnailUrl: String,
)
