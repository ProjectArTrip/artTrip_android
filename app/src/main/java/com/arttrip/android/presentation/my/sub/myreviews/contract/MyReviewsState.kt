package com.arttrip.android.presentation.my.sub.myreviews.contract

data class MyReviewsState(
    val isLoading: Boolean = false,
    val reviewTotalCount: Int? = null,
    val isRemoveDialogVisible: Boolean = false,
) {
    val isEmpty: Boolean
        get() = !isLoading && reviewTotalCount == null
}
