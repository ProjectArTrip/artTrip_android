package com.arttrip.android.presentation.my.sub.myreviews.contract

import com.arttrip.android.domain.model.review.MyReview

sealed interface MyReviewsIntent {
    data object BackClicked : MyReviewsIntent

    data object DeleteReviewClicked : MyReviewsIntent

    data class EditReviewClicked(
        val review: MyReview,
    ) : MyReviewsIntent

    data object RemoveDialogDismissed : MyReviewsIntent

    data object RemoveConfirmClicked : MyReviewsIntent
}
