package com.arttrip.android.data.remote.model.review

data class ReviewPageResDto<T>(
    val reviewList: List<T> = emptyList(),
    val nextCursor: Int?,
    val hasNext: Boolean,
    val reviewTotalCount: Int,
)
