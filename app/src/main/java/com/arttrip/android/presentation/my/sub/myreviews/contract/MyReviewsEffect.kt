package com.arttrip.android.presentation.my.sub.myreviews.contract

sealed interface MyReviewsEffect {
    data object NavigateBack : MyReviewsEffect

    data class NavigateToReviewEdit(
        val reviewId: Int,
    ) : MyReviewsEffect

    data object RefreshReviews : MyReviewsEffect
}
