package com.arttrip.android.domain.model.review

data class UserReview(
    val id: Int,
    val exhibitionTitle: String,
    val visitedDate: String,
    val content: String,
    val thumbnailUrl: String,
)
