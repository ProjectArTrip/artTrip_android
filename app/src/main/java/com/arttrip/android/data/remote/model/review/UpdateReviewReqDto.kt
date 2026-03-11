package com.arttrip.android.data.remote.model.review

data class UpdateReviewReqDto(
    val date: String,
    val content: String,
    val deleteImageIds: List<Int>? = null,
)
