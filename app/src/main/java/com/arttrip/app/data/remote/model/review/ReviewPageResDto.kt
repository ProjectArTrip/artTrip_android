package com.arttrip.app.data.remote.model.review

data class ReviewPageResDto<T>(
    val reviews: List<T> = emptyList(),
    val nextCursor: Int?,
    val hasNext: Boolean,
    val reviewTotalCount: Int,
)
