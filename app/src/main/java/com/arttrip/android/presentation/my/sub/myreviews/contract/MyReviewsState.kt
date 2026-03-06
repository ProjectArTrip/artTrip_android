package com.arttrip.android.presentation.my.sub.myreviews.contract

import com.arttrip.android.domain.model.review.MyReview

data class MyReviewsState(
    val isLoading: Boolean = false,
    val reviews: List<MyReview> = emptyList(),
    val reviewTotalCount: Int? = null,
    val isRemoveDialogVisible: Boolean = false,
) {
    val isEmpty: Boolean
        get() = !isLoading && reviews.isEmpty()
}
